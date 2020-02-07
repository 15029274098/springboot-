package com.learn.seckill.redis;

public class SeckillKey extends BasePrefix {


  public SeckillKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  public static SeckillKey seckillOver = new SeckillKey(0, "so");
  public static SeckillKey seckillPath = new SeckillKey(60, "sg");
  public static SeckillKey getSeckillVerifyCode = new SeckillKey(300, "vc");

}
