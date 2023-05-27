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

    /*注册BeanDefinition*/
    void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException;
}
