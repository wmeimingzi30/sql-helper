/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.strategy.impl;

import com.lb.util.sqlhelper.entity.SqlHelperInfo;
import com.lb.util.sqlhelper.strategy.IStrategy;
import org.apache.ibatis.plugin.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * sql-helper策略的抽象实现
 *
 * @author liangb
 * @date 2019/4/10 10:21
 */
public abstract class AbstractStrategy implements IStrategy {

    private final static Logger logger = LoggerFactory.getLogger(AbstractStrategy.class);

    public Object sqlHelper(SqlHelperInfo sqlHelperInfo, String sql, Invocation invocation) throws Exception {
        if (sqlHelperInfo != null) {
            boolean open = sqlHelperInfo.isOpen();
            if (open) {  //如果供开启
                printSqlInfo(sqlHelperInfo.getDesc(), sql);
                boolean timing = sqlHelperInfo.isTiming();
                if (timing) {
                    //开启统计时间
                    return timing(sqlHelperInfo, invocation);
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 统计时间</br>
     * 默认实现：使用System.currentTimeMillis(),并且打印日志</br>
     * 使用者可以重写这个方法，实现自己的统计时间思路。
     *
     * @param sqlHelperInfo
     * @param invocation
     * @return
     * @throws Exception
     */
    protected Object timing(SqlHelperInfo sqlHelperInfo, Invocation invocation) throws Exception {
        //时间统计
        long start = System.currentTimeMillis();
        Object object = invocation.proceed();
        long used = (System.currentTimeMillis() - start);
        logger.info("用时：" + used);
        String waring = sqlHelperInfo.getWarning();
        if (!StringUtils.isEmpty(waring)) {  //提供了警告
            if (used > sqlHelperInfo.getWarningValue()) {
                //发送警告
                sendWarning(waring);
            }
        }
        return object;
    }

    /**
     * 打印sql</br>
     * 默认实现：打印日志
     *
     * @param sqlDesc sql的描述信息
     * @param sql
     */
    protected void printSqlInfo(String sqlDesc, String sql) {
        logger.info(sqlDesc + sql);
    }

    /**
     * 发送警告信息</br>
     * 默认实现：打印error级别的日志</br>
     * 使用者可以实现自己的方式，比如邮件，或者短信。
     *
     * @param waringValue
     */
    protected void sendWarning(String waringValue) {
        logger.error(waringValue);
    }
}
