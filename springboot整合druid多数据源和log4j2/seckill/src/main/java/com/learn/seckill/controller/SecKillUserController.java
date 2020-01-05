package com.learn.seckill.controller;

import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.service.SecKillUserService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/getUsers")
    public List<SecKillUser> selectAllUser() {
        log.warn("查询成功");
        log.error("查询成功");
        return secKillUserService.selectAllUser();
    }

    @PostMapping("/insertUser")
    public void insertUser(@RequestBody SecKillUser secKillUser) {
        secKillUserService.insertUser(secKillUser);
    }
}
