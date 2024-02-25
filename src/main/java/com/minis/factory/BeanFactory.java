package com.minis.factory;

import com.minis.beans.BeansException;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:04
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;

    /*
     *   registryBeanDefinition >> registryBean
     *       为了和Spring框架内的方法名保持一致，所以这里把 registryBeanDefinition 方法修改为 registryBean，参数改为 beanName 和 obj。
     *       其实这个方法的目的也变了，之前的 registryBeanDefinition 目的是注册一个 BeanDefinition，
     *       而 registryBean 方法的目的则是注册一个 Bean 实例 ，所以这里的 obj 是 bean 实例
     * */
    void registerBean(String beanName, Object obj);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);
}





