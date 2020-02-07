package com.learn.seckill.controller;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.domain.SeckillOrder;
import com.learn.seckill.result.CodeMsg;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.vo.GoodsVo;
import com.learn.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Administrator
 */
@Controller
@RequestMapping("/order")
public class OrderController {
  @Autowired
  private OrderService orderService;
  @Autowired
  private GoodService goodsService;


  @RequestMapping(value = "/detail", method = RequestMethod.GET)
  @ResponseBody
  public Result<OrderDetailVo> info(SecKillUser seckillUser,
                                    @RequestParam("orderId") long orderId) {
    if (seckillUser == null) {
      return Result.error(CodeMsg.SERVER_ERROR);
    }

    OrderInfo orderInfo = orderService.getOrderByOrderId(orderId);
    if (orderInfo == null) {
      return Result.error(CodeMsg.ORDER_NOT_EXISTS);
    }
    long goodsId = orderInfo.getGoodsId();
    GoodsVo goodsVo = goodsService.selectSeckillGoodById(goodsId);
    OrderDetailVo orderDetailVo = new OrderDetailVo();
    orderDetailVo.setGoods(goodsVo);
    orderDetailVo.setOrder(orderInfo);
    return Result.success(orderDetailVo);
  }

}
