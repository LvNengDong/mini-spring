package com.minis.aop;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 17:20
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object target);
}
