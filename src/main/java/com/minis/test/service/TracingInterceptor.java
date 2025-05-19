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
        System.out.println("method " + methodInvocation.getMethod() + " is called on " + methodInvocation.getThis() + " with args " + methodInvocation.getArguments());
        Object ret = methodInvocation.proceed();
        System.out.println("method " + methodInvocation.getMethod() + " returns " + ret);
        return ret;
    }
}
