package com.minis.aop;

/**
 * @Author lnd
 * @Description
 *      Advisor 持有拦截器，提供设置/获取拦截器的方法（主要是组装逻辑）
 * @Date 2025/5/19 21:20
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();
    void setMethodInterceptor(MethodInterceptor methodInterceptor);
    Advice getAdvice();
}
