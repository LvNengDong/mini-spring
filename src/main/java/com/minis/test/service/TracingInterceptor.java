package com.minis.test.service;

import com.minis.aop.MethodInterceptor;
import com.minis.aop.MethodInvocation;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 21:17
 */
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("method " + methodInvocation.getMethod() + " is called on " + methodInvocation.getThis() + " with args " + methodInvocation.getArguments()); // 方法执行前的增强逻辑
        Object ret = methodInvocation.proceed(); // 调用被代理对象执行业务逻辑
        System.out.println("method " + methodInvocation.getMethod() + " returns " + ret); // 方法执行后的增强逻辑
        return ret;
    }
}
