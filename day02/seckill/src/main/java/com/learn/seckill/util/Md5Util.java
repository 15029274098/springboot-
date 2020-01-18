package com.learn.seckill.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * 1.客户端：密码加固定盐值
 * 2,服务端：密码加随机盐值
 */
public class Md5Util {
    public static final String SALT = "1a2b3c4d";

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 模拟注册的时候前端生成的密码，明文加固定盐值SALT
     * @param inputPass 输入框密码
     * @return 加密后的密码
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 模拟后端获取前端加密后的密码，前端密码+随机盐值，注册的时候需要将盐值存到数据库
     * @param formPass 前端加密后的密码
     * @param salt 后端随机生成的盐值
     * @return 存到数据库的密码
     */
    public static String FormPassToDbPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 模拟注册，从前端和后端加密后的密码生成到数据库
     * @param inputPass 前端加密密码
     * @param salt 后端盐值
     * @return 数据库两次加密后密码
     */
    public static String inputPassToDbPass(String inputPass, String salt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = FormPassToDbPass(formPass, salt);
        return dbPass;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(FormPassToDbPass(inputPassToFormPass("123456"),"123456"));
        System.out.println(inputPassToDbPass("123456","123456"));

    }
}
