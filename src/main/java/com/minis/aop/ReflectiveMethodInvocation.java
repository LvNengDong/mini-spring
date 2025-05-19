package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 21:38
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object proxy;
    protected final Object target;
    protected final Method method;
    protected Object[] arguments;
    private Class targetClass;

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class targetClass) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Object getThis() {
        return null;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }
}
