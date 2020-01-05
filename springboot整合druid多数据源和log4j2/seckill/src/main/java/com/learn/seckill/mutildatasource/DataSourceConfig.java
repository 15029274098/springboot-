package com.learn.seckill.mutildatasource;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Administrator
 * 配置数据库信息
 */
@Configuration
public class DataSourceConfig {
    /**
     * 商品库
     */
    public static final String DB_SECKILL_GOOD = "master";
    /**
     * 用户库
     */
    public static final String DB_SECKILL_USER = "slave";

    /**
     * name中master作为主数据源
     * (@Primary)该注解声明是默认数据源
     *
     * @return 商品数据源
     */
    @Bean(name = "master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource goodDateSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * name中slave作为从数据源
     * (@Primary)该注解声明是默认数据源
     *
     * @return 用户数据源
     */
    @Bean(name = "slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource userDateSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 默认数据源配置和多数据源配置
     *
     * @return 数据源
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(goodDateSource());
        // 配置多数据源
        Map<Object, Object> dataBaseMap = new HashMap<>(16);
        dataBaseMap.put(DB_SECKILL_USER, userDateSource());
        dataBaseMap.put(DB_SECKILL_GOOD, goodDateSource());
        dynamicDataSource.setTargetDataSources(dataBaseMap);
        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver()
                .getResources("classpath:mybatis/*.xml");
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setMapperLocations(resources);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    /**
     * 事务管理
     *
     * @param dataSource 数据源
     * @return 事务管理
     */
    @Bean(name = "sqlTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

