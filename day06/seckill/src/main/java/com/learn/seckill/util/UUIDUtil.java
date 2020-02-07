package com.learn.seckill.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author Administrator
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");
    }
    public static long workId() {
        Random r = new Random();
        long n = r.nextInt(100000);
        long m = r.nextInt(100000);
        return n+m;
    }

    public static void main(String[] args) {
        System.out.println(workId());
    }

}
