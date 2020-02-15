package com.learn.seckill.redis;

/**
 * @author Administrator
 */
public class OrderKey extends BasePrefix {

  public OrderKey(String prefix) {
    super(prefix);
  }

  public static OrderKey getOrderByGoodsUid = new OrderKey("moug");

}
