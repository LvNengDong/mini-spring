package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 11:42
 */
public class AfterReturningAdviceInterceptor implements AfterAdvice, MethodInterceptor {
    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Object retVal = mi.proceed();
        this.advice.afterReturning(retVal, mi.getMethod(), mi.getArguments(), mi.getThis());
        return retVal;
    }
}
