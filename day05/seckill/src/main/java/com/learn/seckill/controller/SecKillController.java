package com.learn.seckill.controller;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.domain.SeckillOrder;
import com.learn.seckill.redis.GoodsKey;
import com.learn.seckill.redis.OrderKey;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.service.SecKillOrderService;
import com.learn.seckill.service.SecKillUserService;
import com.learn.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 */
@Controller
@Slf4j
@RequestMapping("/seckill")
public class SecKillController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private SecKillOrderService secKillOrderService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SecKillUserService secKillUserService;
    @Autowired
    private RedisService redisService;

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
    public Result<OrderInfo> doSecKill(SecKillUser secKillUser, @RequestParam("goodsId") long goodsId) {
        if (secKillUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        // 1.判断库存
        GoodsVo goodsVo = goodService.selectSeckillGoodById(goodsId);
        int stockCount = goodsVo.getStockCount();
        // 如果小于等于0说明秒杀已经结束了
        if (stockCount <= 0) {
            return Result.error(CodeMsg.SECKILL_OVER);
        }
        // 2.判断用户是否已经秒杀到
        SeckillOrder seckillOrder = redisService.get(OrderKey.getOrderByGoodsUid,""+secKillUser.getId()+"_"+goodsId,SeckillOrder.class);
        if (seckillOrder == null) {
            seckillOrder = secKillOrderService.getSeckillOrderByUseIdGoodsId(secKillUser.getId(), goodsId);
            redisService.set(OrderKey.getOrderByGoodsUid,""+secKillUser.getId()+"_"+goodsId,seckillOrder);
        }

        if (seckillOrder != null) {
            return Result.error(CodeMsg.REPEAT_SECKILL);
        }
        // 3.减库存，下订单，写入秒杀订单,放在一个事务中
        OrderInfo orderInfo = orderService.secKill(secKillUser, goodsVo);
        // 4.清除页面缓存
        redisService.delete(GoodsKey.getGoodsList, "");
        return Result.success(orderInfo);
    }
}
