package com.learn.seckill.redis;

public class GoodsKey extends BasePrefix {

  public GoodsKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  public static GoodsKey getGoodsList = new GoodsKey(600, "gl");
  public static GoodsKey getDetail = new GoodsKey(600, "gd");
  public static GoodsKey getGoodsId = new GoodsKey(600, "gi");
  public static GoodsKey getSeckillDetailStock = new GoodsKey(0, "gs");


}
