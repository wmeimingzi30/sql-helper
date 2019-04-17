/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.strategy;

import com.lb.util.sqlhelper.entity.SqlHelperInfo;
import org.apache.ibatis.plugin.Invocation;

/**
 * 相应的策略接口
 *
 * @author liangb
 * @date 2019/4/9 10:00
 */
public interface IStrategy {

    /**
     * 策略接口
     *
     * @param sqlHelperInfo 策略信息
     * @param sql           sql信息包含参数
     * @param invocation
     * @return
     * @throws Exception
     */
    Object sqlHelper(SqlHelperInfo sqlHelperInfo, String sql, Invocation invocation) throws Exception;

}
