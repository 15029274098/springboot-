package com.learn.seckill.rabbitmq;

import com.learn.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class MqSender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sender(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send message:" + msg);
        amqpTemplate.convertAndSend(MqConfig.QUEUE, msg);
    }

    public void sendTopic(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send topic message:" + msg);
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key1", "topic1:" + msg);
        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE, "topic.key2", "topic2:" + msg);
    }

    public void sendFanout(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send fanout message:" + msg);
        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE,"", msg);
    }

    public void sendHeader(Object message) {
        String msg = RedisService.beanToString(message);
        log.info("send header message:" + msg);
        MessageProperties mp = new MessageProperties();
        mp.setHeader("header1", "value1");
        mp.setHeader("header2", "value2");
        Message msgs = new Message(msg.getBytes(), mp);
        amqpTemplate.convertAndSend(MqConfig.HEADER_EXCHANGE, "", msgs);
    }

    public void sendSecKillMessage(SecKillMessage secKillMessage) {
        log.info("异步下订单，减库存");
        String msg = RedisService.beanToString(secKillMessage);
        amqpTemplate.convertAndSend(MqConfig.SECKILL_QUEUE, msg);
    }
}
