package com.learn.seckill.domain;

/** ‘.’
 * @author Administrator
 * @date 2019/8/31 0031 15:15
 */
public class SeckillOrder {
  private Long id;
  private Long goodsId;
  private Long userId;
  private Long orderId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(Long goodsId) {
    this.goodsId = goodsId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }
}
