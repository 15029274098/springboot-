package com.learn.seckill.mutildatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class DataSourceContextHolder {
    /**
     * 线程独立
     */
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    /**
     * 商品库
     */
    public static final String DB_SECKILL_GOOD = "master";


    /**
     * 获取数据源名
     *
     * @return 数据库名
     */
    public static String getDataBaseType() {
        return contextHolder.get();
    }

    /**
     * 设置数据源名（切换数据源）
     *
     * @param dataBase 数据库类型
     */
    public static void setDataBaseType(String dataBase) {
        log.info("设置数据源：" + dataBase);
        contextHolder.set(dataBase);
    }

    /**
     * 清除数据源名
     */
    public static void clearDataBaseType() {
        contextHolder.remove();
    }

}
