package com.minis.aop;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/20 14:26
 */
public interface PointcutAdvisor extends Advisor{
    Pointcut getPointcut();
}
