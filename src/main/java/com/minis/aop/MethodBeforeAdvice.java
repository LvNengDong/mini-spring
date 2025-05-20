package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 11:36
 */
public interface MethodBeforeAdvice extends BeforeAdvice{
    void before(Method method, Object[] args, Object target) throws Throwable;
}
