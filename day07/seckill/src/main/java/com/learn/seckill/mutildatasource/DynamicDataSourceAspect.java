package com.learn.seckill.mutildatasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Administrator
 * 动态切换数据源类
 */
@Aspect
@Component
@Order(-1)
@Slf4j
public class DynamicDataSourceAspect {

    @Before("@annotation(DataSource)")
    public void beforeSwitchDataSource(JoinPoint point) {
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        String dataSource = DataSourceContextHolder.DB_SECKILL_GOOD;
        try {
            // 得到访问的方法对象
            Method method = className.getMethod(methodName, argClass);
            // 判断是否存在@DataSource注解
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource annotation = method.getAnnotation(DataSource.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (Exception e) {
            log.error("动态切换数据源失败");
        }
        // 切换数据源
        DataSourceContextHolder.setDataBaseType(dataSource);
    }

    @After("@annotation(DataSource)")
    public void afterSwitchDataSource(JoinPoint point) {
        DataSourceContextHolder.clearDataBaseType();
    }
}
