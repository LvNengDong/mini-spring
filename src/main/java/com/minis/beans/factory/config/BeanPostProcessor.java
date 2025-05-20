package com.minis.beans.factory.config;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

/**
 * @Author lnd
 * @Description
 * @Date 2024/2/26 23:28
 */
public interface BeanPostProcessor {
    /**
     * 在bean实例化之后，init-method调用之前执行这个步骤
     * */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
    void setBeanFactory(BeanFactory beanFactory);
}
