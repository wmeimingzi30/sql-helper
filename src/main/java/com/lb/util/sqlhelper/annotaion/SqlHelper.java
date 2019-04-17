/*
 * 不忘初心，
 * 方得始终！
 * 初心易得，
 * 始终难守！
 * Copyright (c) 2019.
 */

package com.lb.util.sqlhelper.annotaion;

import java.lang.annotation.*;

/**
 * 为mapper提供注解
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SqlHelper {
    /**
     * 是否开启功能
     * true 开启 false 关闭
     * 默认关闭
     */
    boolean open() default false;

    /**
     * 是否统计执行时间
     * 默认false 不统计
     * 前提：{@link #open }为true，否则忽略此设置
     */
    boolean timing() default false;

    /**
     * 设置警告
     * 默认实现日志打印
     * 使用者可以实现邮件、短信等警告方式
     * 可以设置多个，用“，”分割
     */
    String warning() default "";

    /**
     * 一个sql的执行时间的阈值
     * 当超过这个时间后，会检查{@link #warning} 来发送警告信息
     * 前提：{@link #timing} 设置为true
     * 单位：毫秒
     * 默认：10000
     */
    int warningValue() default 10000;

    /**
     * 对于这些设置，系统提供一个默认实现
     * 使用者，也可以指定自己的策略
     * 程序通过getBean的获取。
     * 如果没有设置，则采用默认策略
     */
    String strategy() default "";

    /***
     * 打印的前缀
     * @return
     */
    String desc() default "sql_helper_print";

}
