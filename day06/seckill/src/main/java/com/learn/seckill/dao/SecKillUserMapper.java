package com.learn.seckill.dao;

import com.learn.seckill.domain.SecKillUser;
import org.apache.ibatis.annotations.Param;

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
     * 根据手机号查询
     *
     * @return 单个用户
     */
    SecKillUser selectOneUserByMobile(@Param("username") String username);

    /**
     * 插入用户
     *
     * @param secKillUser 用户
     */
    void insertUser(SecKillUser secKillUser);
}
