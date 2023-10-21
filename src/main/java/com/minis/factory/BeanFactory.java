package com.minis.factory;

import com.minis.beans.BeansException;
import com.minis.beans.BeanDefinition;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:04
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeansException;


    void registerBeanDefinition(BeanDefinition beanDefinition);

    /*
     *   registryBeanDefinition >> registryBean
     *       为了和Spring框架内的方法名保持一致，所以这里把 registryBeanDefinition 方法修改为 registryBean，参数改为 beanName 和 obj。
     *       TODO 至于这个方法到底是想要注册 BeanDefinition 还是 bean，需要再看一看
     * */

    void registerBean(String beanName, Object obj);

    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class getType(String name);
}





