package com.learn.seckill.rabbitmq;

import com.learn.seckill.domain.OrderInfo;
import com.learn.seckill.domain.SecKillUser;
import com.learn.seckill.redis.GoodsKey;
import com.learn.seckill.redis.RedisService;
import com.learn.seckill.service.GoodService;
import com.learn.seckill.service.OrderService;
import com.learn.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class MqReceiver {
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodService goodService;

    @RabbitListener(queues = MqConfig.SECKILL_QUEUE)
    public void receiveSecKillMessage(String message) {
        SecKillMessage SecKillMessage = RedisService.stringToBean(message, SecKillMessage.class);
        SecKillUser secKillUser = SecKillMessage.getSecKillUser();
        long goodsId = SecKillMessage.getGoodsId();
        GoodsVo goodsVo = redisService.get(GoodsKey.getGoodsId, "" + goodsId, GoodsVo.class);
        if (goodsVo == null) {
            goodsVo = goodService.selectSeckillGoodById(goodsId);
            redisService.set(GoodsKey.getGoodsId, "" + goodsId, goodsVo);
        }
        //3.减库存，下订单，写入秒杀订单,放在一个事务中
        orderService.secKill(secKillUser, goodsVo);
        // 4.清除页面缓存
        redisService.delete(GoodsKey.getGoodsList, "");
    }

    @RabbitListener(queues = MqConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
    }


    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receiveTopicQueue1(String message) {
        log.info("receive message:" + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receiveTopicQueue2(String message) {
        log.info("receive message:" + message);
    }

    @RabbitListener(queues = MqConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info("receive message:" + new String(message));
    }
}
