package com.minis.core;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;

/**
 * @Author lnd
 * @Description 提供两个核心功能
 *  1、获取bean（getBean）
 *  2、注册一个BeanDefinition
 * @Date 2023/5/1 19:13
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    @Deprecated
    void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException;


    Boolean containsBean(String name);
    /*
    为了和 Spring 框架内的方法名保持一致，我们把 BeanFactory 接口中定义的 registryBeanDefinition 方法修改为 registryBean，
    参数修改为 beanName 与 obj。其中，obj 为 Object 类，指代与 beanName 对应的 Bean 的信息。
    */
    void registerBean(String beanName, Object obj);
}
