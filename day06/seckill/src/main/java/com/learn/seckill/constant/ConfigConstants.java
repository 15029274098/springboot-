package com.learn.seckill.constant;

/**
 * @author Administrator
 */
public interface ConfigConstants {
    /**
     * mybatis的配置文件所在位置
     */
    String MYBAITS_XML_PATH = "classpath:mybatis/*.xml";
    //redis主节点名称
    String SENTINEL_MASTER = "mymaster";
    // 连接主节点密码
    String SENTINEL_PASSWORD = "123456";
}
