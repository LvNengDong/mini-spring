package com.minis.aop;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 14:24
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
