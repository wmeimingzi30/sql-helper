/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.strategy;

import com.lb.util.sqlhelper.annotaion.SqlHelper;
import com.lb.util.sqlhelper.entity.SqlHelperInfo;

/**
 * 对于一个sql是否需要sql-helper，需要反射获取注解信息</br>
 * 为了避免每次使用反射获取信息，需要对一些执行过的sql信息进行缓存</br>
 * 相应的缓存策略</br>
 * 默认实现使用map进行本地缓存</br>
 * 使用者可以自己实现redis、数据库等其他方式</br>
 * 实现自己的缓存策略注意点：</br>
 * sql-helper信息的有效期</br>
 *
 * @author liangb
 * @date 2019/4/9 10:25
 */
public interface ICacheStrategy {

    /**
     * 通过key（可以是sql的全路径，可以是别名）
     *
     * @param key
     * @return
     */
    SqlHelperInfo getSqlHelperInfo(String key);

    /**
     * 保存一个sqlhelper信息
     *
     * @param key
     * @param sqlHelper
     * @return 返回保存的对象
     */
    SqlHelperInfo setSqlHelperInfo(String key, SqlHelper sqlHelper);
}
