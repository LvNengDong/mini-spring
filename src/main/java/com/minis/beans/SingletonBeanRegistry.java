package com.minis.beans;

/**
 * @Author lnd
 * @Description 单例Bean的接口，将管理单例bean的方法规范好
 * @Date 2023/5/1 23:23
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
