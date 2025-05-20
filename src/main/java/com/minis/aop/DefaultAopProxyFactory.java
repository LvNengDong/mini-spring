package com.minis.aop;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 17:34
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(Object target, Advisor advisor) {
        return new JdkDynamicAopProxy(target, advisor);
    }
}
