package com.minis.aop;

/**
 * @Author lnd
 * @Description 拦截器，实现真正的增强逻辑
 *      1、拦截器不仅会执行增强逻辑
 *      2、它内部也会调用真正的业务逻辑方法
 * @Date 2025/5/19 17:50
 */
public interface Interceptor extends Advice {
}
