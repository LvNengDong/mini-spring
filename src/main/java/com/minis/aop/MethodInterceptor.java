package com.minis.aop;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 17:50
 */
public interface MethodInterceptor extends Interceptor{
    Object invoke(MethodInvocation methodInvocation) throws Throwable;
}
