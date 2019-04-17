/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.interceptor;

import com.lb.util.sqlhelper.annotaion.SqlHelper;
import com.lb.util.sqlhelper.entity.SqlHelperInfo;
import com.lb.util.sqlhelper.property.GlobalSqlHelperSetting;
import com.lb.util.sqlhelper.strategy.ICacheStrategy;
import com.lb.util.sqlhelper.strategy.IStrategy;
import com.lb.util.sqlhelper.util.StrategyUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * 描述：日志打印拦截器
 * 通过反射获取注解，看是否需要打印sql
 * 为了避免每次获取，会对执行过的sql，进行缓存。
 * 作者：liangb
 * 日期：2018/10/15
 * 时间：15:48
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class SqlHelperInterceptor implements Interceptor {

    private final static Logger logger = LoggerFactory.getLogger(SqlHelperInterceptor.class);

    @Autowired
    private GlobalSqlHelperSetting globalSqlHelperSetting;

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String namespace = mappedStatement.getId();
        //判断是否存在
        ICacheStrategy cacheStrategy = StrategyUtil.getCacheStrategy(globalSqlHelperSetting.getCacheStrategyKey());
        IStrategy strategy = StrategyUtil.getStrategy(globalSqlHelperSetting.getStrategyKey());
        Object cacheNamespace = cacheStrategy.getSqlHelperInfo(namespace);
        //拼装sql信息和参数信息
        String originalSql = boundSql.getSql().trim();
        originalSql = originalSql.toLowerCase() + invocation.getArgs()[1];
        if (cacheNamespace == null) {
            //没有缓存过
            String className = namespace.substring(0, namespace.lastIndexOf("."));
            String methodName = namespace.substring(namespace.lastIndexOf(".") + 1, namespace.length());
            Method[] ms = Class.forName(className).getMethods();
            SqlHelper annotation = null;
            for (Method m : ms) {
                if (m.getName().equals(methodName)) {
                    annotation = m.getAnnotation(SqlHelper.class);
                    break;
                }
            }
            if (annotation == null) {
                //不需要打印
                cacheStrategy.setSqlHelperInfo(namespace, null);
                return invocation.proceed();
            } else {
                SqlHelperInfo sqlHelperInfo = cacheStrategy.setSqlHelperInfo(namespace, annotation);
                return strategy.sqlHelper(sqlHelperInfo, originalSql, invocation);
            }
        } else {
            if (cacheNamespace instanceof SqlHelperInfo) {
                SqlHelperInfo logSqlHelper = (SqlHelperInfo) cacheNamespace;
                return strategy.sqlHelper(logSqlHelper, originalSql, invocation);
            }
        }
        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
    }

    /**
     * 反射工具
     */
    private static class ReflectUtil {
        /**
         * 利用反射获取指定对象的指定属性
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            final Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 修改属性值，此处没有使用
         *
         * @param obj
         * @param fieldName
         * @param fieldValue
         */
        public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
            final Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
