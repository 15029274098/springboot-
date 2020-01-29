package com.learn.seckill.controller;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.domain.SeckillOrder;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.service.SecKillOrderService;
import com.learn.seckill.service.SecKillUserService;
import com.learn.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 */
@Controller
@Slf4j
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
    @PostMapping("/seckill/do_seckill")
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
}
