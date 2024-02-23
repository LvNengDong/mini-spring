package com.minis.factory;

import com.minis.beans.BeansException;
import com.minis.beans.BeanDefinition;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:04
 */
public interface BeanFactory {
    /**
     * 获取Bean
     * */
    Object getBean(String beanName) throws BeansException;
    /*
    * 注册 BeanDefinition
    * */
    void registerBeanDefinition(BeanDefinition beanDefinition);
}
