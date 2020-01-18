package com.learn.seckill.util;

import java.util.UUID;

/**
 * @author Administrator
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
