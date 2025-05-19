package com.minis.aop;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 17:30
 */
@Slf4j
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }


    @Override
    public Object getProxy() {
        Object object = Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
        return object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("-----before call real object, dynamic proxy........");
        return method.invoke(target, args);
    }
}
