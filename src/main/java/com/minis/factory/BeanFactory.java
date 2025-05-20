package com.minis.factory;

import com.minis.beans.BeansException;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:04
 */
public interface BeanFactory {
    /**
     * 获取一个 Bean
     * */
    Object getBean(String beanName) throws BeansException;

    /**
     * 注册一个 Bean 实例
     */
    void registerBean(String beanName, Object obj);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);
}
