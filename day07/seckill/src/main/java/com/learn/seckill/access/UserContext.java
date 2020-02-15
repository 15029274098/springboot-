package com.learn.seckill.access;

import com.learn.seckill.domain.SecKillUser;

/**
 * @author Administrator
 */
public class UserContext {

    private static ThreadLocal<SecKillUser> userHolder = new ThreadLocal<SecKillUser>();

    public static void setUser(SecKillUser user) {
        userHolder.set(user);
    }

    public static SecKillUser getUser() {
        return userHolder.get();
    }

}