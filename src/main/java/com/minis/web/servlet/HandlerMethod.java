package com.minis.web.servlet;

import com.sun.org.apache.bcel.internal.classfile.MethodParameter;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:15
 */
@Data
public class HandlerMethod {
    private Object bean;
    private Class<?> beanType;
    private Method method;
    private MethodParameter[] parameters;
    private Class<?> returnType;
    private String description;
    private String className;
    private String methodName;

    public HandlerMethod(Method method, Object obj) {
        this.setMethod(method);
        this.setBean(obj);
    }
}
