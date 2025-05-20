package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 21:38
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    protected final Object proxy; // 代理对象
    protected final Object target; // 被代理对象
    protected final Method method;
    protected Object[] arguments;
    private Class targetClass; // 被代理对象的类

    public ReflectiveMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class targetClass) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }
}
