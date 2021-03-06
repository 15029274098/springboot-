package com.learn.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan("com.learn.seckill.dao")
public class SecKillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }
}
