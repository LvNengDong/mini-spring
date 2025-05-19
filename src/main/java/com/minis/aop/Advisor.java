package com.minis.aop;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 21:20
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();
    void setMethodInterceptor(MethodInterceptor methodInterceptor);
}
