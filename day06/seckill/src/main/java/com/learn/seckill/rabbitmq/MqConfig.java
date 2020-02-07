package com.learn.seckill.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
public class MqConfig {
    /**
     * 秒杀队列
     */
    public static final String SECKILL_QUEUE = "seckill.queue";
    /**
     * direct
     */
    public static final String QUEUE = "queue";
    /**
     * topic
     */
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    /**
     * fanout
     */
    public static final String FANOUT_EXCHANGE = "fanout.exchange";
    /**
     * header
     */
    public static final String HEADER_QUEUE = "header.queue";
    public static final String HEADER_EXCHANGE = "header.exchange";

    /**
     * 1.第一种方式：Direct模式 交换机Exchange
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    /**
     * 第二种方式：topic方式
     *
     * @return
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
    }


    /**
     * 第三种模式：fanout模式
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    /**
     * header模式
     * @return
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADER_EXCHANGE);
    }
    @Bean
    public Queue headersQueue() {
        return new Queue(HEADER_QUEUE, true);
    }
    @Bean
    public Binding headersBinding() {
        Map<String,Object> map = new HashMap<>(15);
        map.put("header1","value1");
        map.put("header2","value2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }
}
