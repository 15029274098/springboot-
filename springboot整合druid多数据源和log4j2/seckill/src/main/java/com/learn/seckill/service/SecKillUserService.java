package com.learn.seckill.service;

import com.learn.seckill.dao.SecKillUserMapper;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.mutildatasource.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SecKillUserService {
    @Autowired
    private SecKillUserMapper secKillUserMapper;

    @DataSource
    public List<SecKillUser> selectAllUser() {
        return secKillUserMapper.selectAllUser();
    }

    @DataSource
    @Transactional(rollbackFor = Exception.class)
    public void insertUser(SecKillUser secKillUser) {
        secKillUserMapper.insertUser(secKillUser);
    }


}
