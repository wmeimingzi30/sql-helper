/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.util;

import com.lb.util.sqlhelper.strategy.ICacheStrategy;
import com.lb.util.sqlhelper.strategy.IStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据相应的key获取相应的实现
 *
 * @author liangb
 * @date 2019/4/9 10:53
 */
public final class StrategyUtil {

    public static Map<String, ICacheStrategy> cacheStrategyMap = new ConcurrentHashMap<String, ICacheStrategy>();
    public static Map<String, IStrategy> strategyMap = new ConcurrentHashMap<String, IStrategy>();

    /**
     * 获取相应的缓存策略实现
     *
     * @param key
     * @return
     */
    public static ICacheStrategy getCacheStrategy(String key) {
        return cacheStrategyMap.get(key);
    }

    /**
     * 获取相应的执行策略
     *
     * @param key
     * @return
     */
    public static IStrategy getStrategy(String key) {
        return strategyMap.get(key);
    }


}
