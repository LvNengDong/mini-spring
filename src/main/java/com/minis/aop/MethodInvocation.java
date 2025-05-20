package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 *  MethodInvocation 就是以前通过反射方法调用业务逻辑的那一段代码的包装
 *      method.invoke(target, args);
 * @Date 2025/5/19 17:52
 */
public interface MethodInvocation {
    Method getMethod();
    Object[] getArguments();

    /**
     * 返回被代理的对象
     * @return
     */
    Object getThis();

    /**
     * 调用被代理对象中的被代理方法
     */
    Object proceed() throws Throwable;
}
