package com.minis.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/1 15:41
 */
@Target(ElementType.METHOD) // 注解作用于方法上
@Retention(RetentionPolicy.RUNTIME) // 注解保留到运行时
public @interface RequestMapping {
    String value() default "";  // 用来接收配置的 URL 路径
}
