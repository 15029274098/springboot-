package com.learn.seckill.rabbitmq;

import com.learn.seckill.domain.SecKillUser;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class SecKillMessage {
    private SecKillUser secKillUser;
    private long goodsId;
}
