package com.learn.seckill.service;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.domain.SeckillOrder;
import com.learn.seckill.mutildatasource.DataSource;
import com.learn.seckill.redis.OrderKey;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.redis.SeckillKey;
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
    private GoodService goodService;
    @Autowired
    private SecKillOrderService secKillOrderService;
    @Autowired
    private RedisService redisService;

    @DataSource
    @Transactional
    public OrderInfo secKill(SecKillUser secKillUser, GoodsVo goodsVo) {
        // flag为true说明秒杀成功，false说明商品被秒杀完了
        boolean flag = goodService.reduceStock(goodsVo.getId());
        if (flag) {
            return secKillOrderService.createOrder(secKillUser, goodsVo);
        } else {
            setSecKillOver(goodsVo.getId());
            return null;
        }
    }

    @DataSource
    public OrderInfo getOrderByOrderId(long orderId) {
        return secKillOrderService.getOrderByOrderId(orderId);
    }

    public long getSecKillResult(Long id, long goodsId) {
        SeckillOrder seckillOrder = secKillOrderService.getSeckillOrderByUseIdGoodsId(id,goodsId);
        if (seckillOrder != null) {
            return seckillOrder.getOrderId();
        } else {
            boolean isOver = getSecKillOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    private void setSecKillOver(Long goodsId) {
        redisService.set(SeckillKey.seckillOver, "" + goodsId, true);
    }
    private boolean getSecKillOver(long goodsId) {
        return redisService.exists(SeckillKey.seckillOver, "" + goodsId);
    }
}
