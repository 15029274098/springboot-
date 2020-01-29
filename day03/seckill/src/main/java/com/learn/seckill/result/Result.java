package com.learn.seckill.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装结果（1.状态码2.信息3.数据（数据类型很多，所以选用泛型））
 * 1.成功时调用方法
 * 2.失败时调用的方法
 *
 * @author Administrator
 * @param <T>
 */
public class Result<T> {
    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg codeMsg) {
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }
    public Map<String,Object> toQueryString(){
        Map<String,Object> map = new HashMap<>(10);
        map.put("code",getCode());
        map.put("msg",getMsg());
        return map;
    }
}
