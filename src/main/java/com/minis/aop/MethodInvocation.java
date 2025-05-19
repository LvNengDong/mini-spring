package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description MethodInvocation 实际上就是以前通过反射方法调用业务逻辑的那一段代码的包装
 * @Date 2025/5/19 17:52
 */
public interface MethodInvocation {
    Method getMethod();
    Object[] getArguments();
    Object getThis();
    Object proceed() throws Throwable;
}
