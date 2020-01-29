package com.learn.seckill.result;

import java.util.HashMap;
import java.util.Map;

public class CodeMsg {

  public static CodeMsg ACCESS_LIMIT = new CodeMsg(11000,"访问次数太频繁");
  private int code;
  private String msg;

  public CodeMsg(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  /**
   * 通用异常
   */
  public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "访问太频繁！");
  public static CodeMsg SUCCESS = new CodeMsg(0, "success");
  public static final CodeMsg BIND_ERROR = new CodeMsg(500105, "参数校验异常：%s");
  public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
  public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500101, "密码不能为空");
  public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500102, "密码错误");
  public static CodeMsg MOBILE_EMPTY = new CodeMsg(500103, "手机号不能为空");
  public static final CodeMsg MOBILE_NOT_EXISIT = new CodeMsg(50011, "手机号不存在");
  public static CodeMsg MOBILE_PATTERN = new CodeMsg(500104, "手机号格式不正确");
  public static CodeMsg SECKILL_FAIL = new CodeMsg(500107, "秒杀失败");
  public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500108, "验证码错误");
  public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
  public static CodeMsg LOGIN_EMPTY = new CodeMsg(-200,"请登录，再来访问");

  //登录模块 5002XX

  //商品模块 5003XX

  //订单模块 5004XX

  /**
   * 秒杀模块 5005XX
   *
   * @return
   */
  public static final CodeMsg SECKILL_OVER = new CodeMsg(500505, "商品秒杀完毕");
  public static final CodeMsg REPEAT_SECKILL = new CodeMsg(500506, "商品不能重复秒杀");
  public static final CodeMsg REQUEST_SECKILL_ERROR = new CodeMsg(500107, "请求非法");



  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public CodeMsg fillArgs(Object... args) {
    int code = this.code;
    String message = String.format(this.msg, args);
    return new CodeMsg(code, message);
  }
}
