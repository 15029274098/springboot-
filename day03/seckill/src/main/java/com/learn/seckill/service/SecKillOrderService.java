package com.learn.seckill.service;

import com.learn.seckill.dao.SecKillOrderMapper;
import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.domain.SeckillOrder;
import com.learn.seckill.mutildatasource.DataSource;
import com.learn.seckill.util.IdWorker;
import com.learn.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Administrator
 */
@Service
public class SecKillOrderService {
    @Autowired
    private SecKillOrderMapper secKillOrderMapper;
    @Autowired
    private IdWorker idWorker;

    @DataSource("slave")
    public SeckillOrder getSeckillOrderByUseIdGoodsId(Long userId, Long goodsId) {
        return secKillOrderMapper.getSeckillOrderByUseIdGoodsId(userId, goodsId);
    }
    @DataSource
    @Transactional
    public OrderInfo createOrder(SecKillUser secKillUser, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        long workerId = idWorker.nextId();
        orderInfo.setId(workerId);
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsImg(goodsVo.getGoodsImg());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getSeckillGoodsPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setPayDate(new Date());
        orderInfo.setStatus(0);
        orderInfo.setUserId(secKillUser.getId());
        secKillOrderMapper.insertOrderInfo(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idWorker.nextId());
        seckillOrder.setGoodsId(goodsVo.getId());
        seckillOrder.setUserId(secKillUser.getId());
        seckillOrder.setOrderId(workerId);
        secKillOrderMapper.insertSecKillOrder(seckillOrder);
        return orderInfo;
    }
}
