package com.learn.seckill.controller;

import com.learn.seckill.domain.Goods;
import com.learn.seckill.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Administrator
 */
@RestController
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/goods")
    List<Goods> selectAllGood() {
        return goodService.selectAllGood();
    }
}
