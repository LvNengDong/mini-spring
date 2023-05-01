package com.minis.core;

/**
 * @Author lnd
 * @Description 管理单例bean
 * @Date 2023/5/1 23:23
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
