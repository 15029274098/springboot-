package com.learn.seckill.mutildatasource;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author Administrator
 * 获取数据源名
 */
@Configuration
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
     * 获取当前数据源并打印日志记录
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        log.error("当前数据源:" + DataSourceContextHolder.getDataBaseType());
        return DataSourceContextHolder.getDataBaseType();
    }
}
