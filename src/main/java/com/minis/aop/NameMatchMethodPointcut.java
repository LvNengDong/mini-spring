package com.minis.aop;

import com.minis.util.PatternMatchUtils;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 14:22
 */
public class NameMatchMethodPointcut implements MethodMatcher, Pointcut {

    @Setter
    private String mappedName = "";

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName)) {
            return true;
        }
        return false;
    }

    /**
     * 判断方法名是否匹配给定的模式
     */
    private boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return null;
    }
}
