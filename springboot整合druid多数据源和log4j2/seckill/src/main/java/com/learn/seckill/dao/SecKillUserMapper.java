package com.learn.seckill.dao;

import com.learn.seckill.domain.SecKillUser;

import java.util.List;

/**
 * @author Administrator
 */
public interface SecKillUserMapper {
    /**
     * 查询所有用户
     *
     * @return
     */
    List<SecKillUser> selectAllUser();

    /**
     * 插入用户
     *
     * @param secKillUser 用户
     */
    void insertUser(SecKillUser secKillUser);
}
