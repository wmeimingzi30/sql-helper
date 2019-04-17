/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.property;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 设置sql-helper的全局设置
 *
 * @author liangb
 * @date 2019/4/9 9:37
 */
@Component
@ConfigurationProperties(prefix = "sql.helper.global")
public class GlobalSqlHelperSetting implements InitializingBean {

    private final static String DEFAULT_CACHE_STRATEGY = "defaultCacheStrategy";

    private final static String DEFAULT_STRATEGY = "defaultStrategy";
    /**
     * 全局警告
     * 优先级：方法>全局
     */
    private String waring;

    /**
     * sql执行时间的阈值
     */
    private Integer waringValue;

    /**
     * 缓存key
     */
    private String cacheStrategyKey;

    /**
     * 行为key
     */
    private String strategyKey;

    public void afterPropertiesSet() throws Exception {
        //如果waringValue没有设置,设置为10s。
        if (waringValue == 0) {
            waringValue = 10000;
        }
        if (StringUtils.isEmpty(cacheStrategyKey))
            cacheStrategyKey = DEFAULT_CACHE_STRATEGY;
        if (StringUtils.isEmpty(strategyKey))
            strategyKey = DEFAULT_STRATEGY;
    }

    public String getWaring() {
        return waring;
    }

    public void setWaring(String waring) {
        this.waring = waring;
    }

    public Integer getWaringValue() {
        return waringValue;
    }

    public void setWaringValue(Integer waringValue) {
        this.waringValue = waringValue;
    }

    public String getCacheStrategyKey() {
        return cacheStrategyKey;
    }

    public void setCacheStrategyKey(String cacheStrategyKey) {
        this.cacheStrategyKey = cacheStrategyKey;
    }

    public String getStrategyKey() {
        return strategyKey;
    }

    public void setStrategyKey(String strategyKey) {
        this.strategyKey = strategyKey;
    }
}
