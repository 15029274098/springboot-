package com.learn.seckill.redis;

public class AccessKey extends BasePrefix {


  public AccessKey(int expireSeconds, String prefix) {
    super(expireSeconds, prefix);
  }

  public static AccessKey access = new AccessKey(300, "access");
  public static AccessKey withExpire(int expireSeconds) {
    return new AccessKey(expireSeconds, "access");
  }

}
