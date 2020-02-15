package com.learn.seckill.controller;

import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.redis.GoodsKey;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.result.Result;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.vo.GoodsDetailVo;
import com.learn.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private RedisService redisService;

    @GetMapping("/goods")
    @ResponseBody
    public List<GoodsVo> selectAllGood() {
        return goodService.selectAllGood();
    }

    @GetMapping("/goods/to_list")
    @ResponseBody
    public String toLogin(HttpServletRequest request, HttpServletResponse response,Model model) {
      /*  if (secKillUser == null) {
            return "login";
        }*/
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (StringUtils.isNotEmpty(html)) {
            System.out.println("详情页查询到页面渲染缓存");
            return html;
        }
        List<GoodsVo> goodsList = goodService.selectAllGood();
        model.addAttribute("goodsList", goodsList);
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        //取缓存
        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> goodsDetail(SecKillUser secKillUser, @PathVariable("goodsId") long goodsId) {
      /*  if (secKillUser == null) {
            return "login";
        }*/
//        String html = redisService.get(GoodsKey.getDetail, "goodsId", String.class);
//        if (StringUtils.isNotEmpty(html)) {
//            return html;
//        }
//        model.addAttribute("user", secKillUser);
        GoodsVo goods = redisService.get(GoodsKey.getDetail, ""+goodsId, GoodsVo.class);
        if (goods == null) {
            goods = goodService.selectSeckillGoodById(goodsId);
            redisService.set(GoodsKey.getDetail, ""+goodsId, goods);
        }

//        model.addAttribute("goods", goods);
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
   /*     model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        WebContext context = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        //手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", context);
        //取缓存
        if (StringUtils.isNotEmpty(html)) {
            redisService.set(GoodsKey.getDetail, "goodsId", html);
        }*/
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoodsVo(goods);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setSeckillStatus(seckillStatus);
        goodsDetailVo.setSeckillUser(secKillUser);
        return Result.success(goodsDetailVo);
    }
}
