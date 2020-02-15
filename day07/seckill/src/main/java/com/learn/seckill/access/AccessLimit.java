package com.learn.seckill.access;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
    //多少秒内可以访问的次数
    int seconds() default 60;

    //最大访问次数
    int maxCount() default 5;

    //默认需要登录
    boolean needLogin() default true;
}