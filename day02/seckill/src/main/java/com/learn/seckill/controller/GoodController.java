package com.learn.seckill.controller;

import com.learn.seckill.domain.Goods;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.SecKillUserService;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Administrator
 */
@Controller
@Slf4j
public class GoodController {
    @Autowired
    private GoodService goodService;
    @Autowired
    private SecKillUserService secKillUserService;
    @Autowired
    private RedisService redisService;

    @GetMapping("/goods")
    public List<Goods> selectAllGood() {
        return goodService.selectAllGood();
    }

    @GetMapping("/goods/to_list")
    public String toLogin(SecKillUser secKillUser,Model model) {
        model.addAttribute("user",secKillUser);
        return "goods_list";
    }
}
