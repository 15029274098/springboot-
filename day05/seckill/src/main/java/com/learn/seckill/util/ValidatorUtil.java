package com.learn.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * 校验手机号格式是否正确
 */

public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("15029479999"));
    }

}
