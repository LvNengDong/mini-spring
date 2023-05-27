package com.minis.beans;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;

/**
 * @Author lnd
 * @Description 提供两个核心功能
 * 1、获取bean（getBean）
 * 2、注册一个BeanDefinition
 * @Date 2023/5/1 19:13
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;


    Boolean containsBean(String name);

    //void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException;

    /**
     * 为了和 Spring 框架内的方法名保持一致，我们把 BeanFactory 接口中定义的 registryBeanDefinition 方法修改为 registryBean，
     * 参数修改为 beanName 与 obj。其中，obj 为 Object 实例，指代与 beanName 对应的 Bean 实例。
     *
     * 之前我们的代码中注册的beanDefinition，现在直接改成注册bean实例，BeanDefinition的注册功能转移
     *
     * @param beanName
     * @param obj
     */
    void registerBean(String beanName, Object obj);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);
}

/*
有一个问题不太明白, 为什么要有BeanFactory和SingletonBeanRegistry这两个接口呢
作者回复: 角色分离。一个是工厂，另一个是仓库。

可以理解为是按接口编程, BeanFactory接口负责Bean的获取，SingletonBeanRegistry接口负责Bean的存储，
一个类同时实现这两个接口，就有完整的IOC实现，例如Spring中的DefaultListableBeanFactory；
*/