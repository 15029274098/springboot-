package com.learn.seckill.controller;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.domain.SeckillOrder;
import com.learn.seckill.rabbitmq.MqSender;
import com.learn.seckill.rabbitmq.SecKillMessage;
import com.learn.seckill.redis.GoodsKey;
import com.learn.seckill.redis.OrderKey;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.service.SecKillOrderService;
import com.learn.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Controller
@Slf4j
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {
    Map<Long, Boolean> localOverMap = new HashMap<>();
    @Autowired
    private GoodService goodService;
    @Autowired
    private SecKillOrderService secKillOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MqSender mqSender;

    /**
     * 1.先判断库存
     * 2.判断订单是否重复秒杀
     * 3.减库存，下订单，写入秒杀订单
     *
     * @param secKillUser
     * @param model
     * @param goodsId
     * @return
     */
    @PostMapping("do_seckill")
    public String toLogin(SecKillUser secKillUser, Model model, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", secKillUser);
        if (secKillUser == null) {
            return "login";
        }
        // 1.判断库存
        GoodsVo goodsVo = goodService.selectSeckillGoodById(goodsId);
        int stockCount = goodsVo.getStockCount();
        // 如果小于等于0说明秒杀已经结束了
        if (stockCount <= 0) {
            model.addAttribute("errMsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }
        // 2.判断用户是否已经秒杀到
        SeckillOrder seckillOrder = secKillOrderService.getSeckillOrderByUseIdGoodsId(secKillUser.getId(), goodsId);
        if (seckillOrder != null) {
            model.addAttribute("errMsg", CodeMsg.REPEAT_SECKILL.getMsg());
            return "seckill_fail";
        }
        // 3.减库存，下订单，写入秒杀订单,放在一个事务中
        OrderInfo orderInfo = orderService.secKill(secKillUser, goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);
        return "order_detail";
    }

    /**
     * 1.先判断库存
     * 2.判断订单是否重复秒杀
     * 3.减库存，下订单，写入秒杀订单
     *
     * @param secKillUser
     * @param goodsId
     * @return
     */
    @PostMapping("/seckill")
    @ResponseBody
    public Result<Integer> doSecKill(SecKillUser secKillUser, @RequestParam("goodsId") long goodsId) {
        if (secKillUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        long stockCount = redisService.decr(GoodsKey.getSeckillDetailStock, "" + goodsId);
        // 1.判断库存
        // 如果小于等于0说明秒杀已经结束了
        if (stockCount < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 2.判断用户是否已经秒杀到
        SeckillOrder seckillOrder = redisService.get(OrderKey.getOrderByGoodsUid, "" + secKillUser.getId() + "_" + goodsId, SeckillOrder.class);
        if (seckillOrder == null) {
            seckillOrder = secKillOrderService.getSeckillOrderByUseIdGoodsId(secKillUser.getId(), goodsId);
            redisService.set(OrderKey.getOrderByGoodsUid, "" + secKillUser.getId() + "_" + goodsId, seckillOrder);
        }

        if (seckillOrder != null) {
            return Result.error(CodeMsg.REPEAT_SECKILL);
        }
        // 3.减库存，下订单，写入秒杀订单,放在一个事务中
//        OrderInfo orderInfo = orderService.secKill(secKillUser, goodsVo);
//        // 4.清除页面缓存
//        redisService.delete(GoodsKey.getGoodsList, "");
        SecKillMessage sm = new SecKillMessage();
        sm.setSecKillUser(secKillUser);
        sm.setGoodsId(goodsId);
        mqSender.sendSecKillMessage(sm);
        // 0代表排队中
        return Result.success(0);
    }

    /**
     * 1.orderId:成功，订单成功返回订单id
     * 2.-1：表示秒杀失败
     * 3.0：表示排队中
     *
     * @param goodsId 商品id
     * @return
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> doSecKillResult(SecKillUser secKillUser, @RequestParam("goodsId") long goodsId) {
        if (secKillUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = orderService.getSecKillResult(secKillUser.getId(), goodsId);
        return Result.success(result);
    }

    /**
     * 启动时就把库存加载到redis
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVoList = goodService.selectAllGood();
        if (goodsVoList == null) {
            return;
        }
        for (GoodsVo goodsVo : goodsVoList) {
            localOverMap.put(goodsVo.getId(), false);
            redisService.set(GoodsKey.getSeckillDetailStock, "" + goodsVo.getId(), goodsVo.getStockCount());
        }
    }
}
