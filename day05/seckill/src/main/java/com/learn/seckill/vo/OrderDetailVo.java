package com.learn.seckill.vo;

import com.learn.seckill.domain.OrderInfo;

/** '.'
 * @author Administrator
 * @date 2019/9/15 0015 23:29
 */
public class OrderDetailVo {

  private OrderInfo order;
  private GoodsVo goods;

  public OrderInfo getOrder() {
    return order;
  }

  public void setOrder(OrderInfo order) {
    this.order = order;
  }

  public GoodsVo getGoods() {
    return goods;
  }

  public void setGoods(GoodsVo goods) {
    this.goods = goods;
  }
}
