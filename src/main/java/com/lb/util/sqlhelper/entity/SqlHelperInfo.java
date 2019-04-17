/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */
package com.lb.util.sqlhelper.entity;

/**
 * sql信息打印信息的实体封装
 * 包含全部的行为，比如{@link #open}等
 *
 * @author liangb
 * @date 2019/4/9 9:21
 */
public final class SqlHelperInfo {
    /**
     * 是否开启功能
     * true 开启 false 关闭
     * 默认关闭
     */
    private boolean open;

    /**
     * 是否统计执行时间
     * 默认false 不统计
     * 前提：{@link #open }为true，否则忽略此设置
     */
    private boolean timing;

    /**
     * 设置警告邮件
     * 可以设置多个，用“，”分割
     */
    private String warning;

    /**
     * 一个sql的执行时间的阈值
     * 当超过这个时间后，会检查{@link #warning} 来发送邮件
     * 前提：{@link #timing} 设置为true
     */
    private Integer warningValue;

    /**
     * 对于这些设置，系统提供一个默认实现
     * 使用者，也可以指定自己的策略
     * 程序通过getBean的获取。
     * 如果没有设置，则采用默认策略
     */
    private String strategy;

    /**
     * 打印的前缀（描述）
     */
    private String desc;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isTiming() {
        return timing;
    }

    public void setTiming(boolean timing) {
        this.timing = timing;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SqlHelperInfo{" +
                "open=" + open +
                ", timing=" + timing +
                ", warning='" + warning + '\'' +
                ", warningValue=" + warningValue +
                ", strategy='" + strategy + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
