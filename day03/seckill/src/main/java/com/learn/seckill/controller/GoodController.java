package com.learn.seckill.controller;

import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.SecKillUserService;
import com.learn.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    public List<GoodsVo> selectAllGood() {
        return goodService.selectAllGood();
    }

    @GetMapping("/goods/to_list")
    public String toLogin(SecKillUser secKillUser, Model model) {
        if (secKillUser == null) {
            return "login";
        }
        List<GoodsVo> goodsList = goodService.selectAllGood();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @GetMapping("/goods/to_detail/{goodsId}")
    public String goodsDetail(SecKillUser secKillUser, @PathVariable("goodsId")long goodsId , Model model) {
        if (secKillUser == null) {
            return "login";
        }
        model.addAttribute("user", secKillUser);
        GoodsVo goods = goodService.selectSeckillGoodById(goodsId);
        model.addAttribute("goods", goods);
        long startDate = goods.getStartDate().getTime();
        long endDate = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;
        //秒杀还没开始
        if (now < startDate) {
            seckillStatus = 0;
            remainSeconds = (int) (startDate - now) / 1000;
            //秒杀已经结束
        } else if (now > endDate) {
            seckillStatus = 2;
            remainSeconds = -1;
            //秒杀正在进行中
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
