package com.learn.seckill.service;

import com.learn.seckill.dao.SecKillOrderMapper;
import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.mutildatasource.DataSource;
import com.learn.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 */
@Service
public class OrderService {
    @Autowired
    private SecKillOrderMapper orderMapper;
    @Autowired
    private GoodService goodService;
    @Autowired
    private SecKillOrderService secKillOrderService;

    @DataSource
    @Transactional
    public OrderInfo secKill(SecKillUser secKillUser, GoodsVo goodsVo) {
        goodService.reduceStock(goodsVo);
        return secKillOrderService.createOrder(secKillUser, goodsVo);
    }
}
