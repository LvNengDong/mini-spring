package com.minis.aop;

import lombok.AllArgsConstructor;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 11:39
 */
public class MethodBeforeAdviceInterceptor implements BeforeAdvice, MethodInterceptor {

    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        this.advice.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.proceed();
    }
}
