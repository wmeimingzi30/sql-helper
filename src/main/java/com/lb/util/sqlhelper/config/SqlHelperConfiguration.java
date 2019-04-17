/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.config;

import com.lb.util.sqlhelper.interceptor.SqlHelperInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 描述：
 * 添加mybatis拦截器
 * @see com.lb.util.sqlhelper.interceptor.SqlHelperInterceptor
 * 初始化策略
 * @see com.lb.util.sqlhelper.strategy.ICacheStrategy
 * @see com.lb.util.sqlhelper.strategy.IStrategy
 * 作者：liangb
 * 日期：2018/10/15
 * 时间：16:47
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class SqlHelperConfiguration implements ApplicationRunner {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    public void run(ApplicationArguments args) throws Exception {
        //初始拦截器
        SqlHelperInterceptor interceptor = new SqlHelperInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(interceptor);
        }
    }
}
