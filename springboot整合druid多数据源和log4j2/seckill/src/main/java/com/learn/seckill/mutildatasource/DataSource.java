package com.learn.seckill.mutildatasource;

import java.lang.annotation.*;

/**
 * 自定义数据源注解，默认是商品库（good）
 *
 * @author Administrator
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default "master";
}
