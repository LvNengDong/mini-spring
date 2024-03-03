package com.minis.beans.factory;

import com.minis.beans.BeansException;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:04
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);
}





