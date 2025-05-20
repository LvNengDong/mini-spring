package com.minis.aop;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 14:25
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
}
