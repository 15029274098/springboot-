package com.learn.seckill.controller;

import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.redis.SeckillUserKey;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.SecKillUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
@Slf4j
public class SecKillUserController {
    @Autowired
    private SecKillUserService secKillUserService;
    @Autowired
    private RedisService redisService;

    @GetMapping("/getUsers")
    public List<SecKillUser> selectAllUser() {
        log.warn("查询成功");
        log.error("查询成功");
        return secKillUserService.selectAllUser();
    }

    @GetMapping("/redis/set")
    public void setUser() {
        log.info("设置值");
        redisService.set(SeckillUserKey.getById,"id","测试");
    }

    @GetMapping("/redis/get")
    public Result<String> getUser() {
        String value = redisService.get(SeckillUserKey.getById,"id",String.class);
        return Result.success(value);
    }


}
