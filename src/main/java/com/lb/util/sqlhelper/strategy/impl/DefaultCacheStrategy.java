/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.strategy.impl;

import com.lb.util.sqlhelper.annotaion.SqlHelper;
import com.lb.util.sqlhelper.entity.SqlHelperInfo;
import com.lb.util.sqlhelper.strategy.ICacheStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认缓存策略实现</br>
 * 使用内存存储</br>
 * 因为使用内存方式，所以系统每次重启都会重新缓存</br>
 *
 * @author liangb
 * @date 2019/4/9 17:00
 */
@Component
@ConditionalOnProperty
public class DefaultCacheStrategy implements ICacheStrategy {

    /**
     * 没有添加注解的sql，使用默认的信息
     */
    private SqlHelperInfo DEFAULT = new SqlHelperInfo();

    /**
     * 以键值对的形式存储已经执行过sql信息
     */
    private Map<String, SqlHelperInfo> sqlHelperInfoMap = new ConcurrentHashMap<String, SqlHelperInfo>();

    public SqlHelperInfo getSqlHelperInfo(String key) {
        return sqlHelperInfoMap.get(key);
    }

    public SqlHelperInfo setSqlHelperInfo(String key, SqlHelper sqlHelper) {
        if (sqlHelper == null) {  //不需要sql_helper
            sqlHelperInfoMap.put(key, DEFAULT);
            return DEFAULT;
        } else {
            //封装SqlHelperInfo对象
            SqlHelperInfo sqlHelperInfo = new SqlHelperInfo();
            sqlHelperInfo.setDesc(sqlHelper.desc());
            sqlHelperInfo.setOpen(sqlHelper.open());
            sqlHelperInfo.setStrategy(sqlHelper.strategy());
            sqlHelperInfo.setTiming(sqlHelper.timing());
            sqlHelperInfo.setWarning(sqlHelper.warning());
            sqlHelperInfo.setWarningValue(sqlHelper.warningValue());
            sqlHelperInfoMap.put(key, sqlHelperInfo);
            return sqlHelperInfo;
        }
    }
}
