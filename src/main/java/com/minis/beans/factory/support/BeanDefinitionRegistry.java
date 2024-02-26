package com.minis.beans.factory.support;

import com.minis.beans.factory.config.BeanDefinition;

/**
 * @Author lnd
 * @Description 一个存放 BeanDefinition 的仓库，可以存放、移除、获取及判断 BeanDefinition 对象
 * @Date 2023/10/15 11:34
 */
public interface BeanDefinitionRegistry {
    /**
    * 注册 BeanDefinition
    * */
    void registerBeanDefinition(String name, BeanDefinition bd);

    /**
     * 删除 BeanDefinition
     * */
    void removeBeanDefinition(String name);

    /**
     * 根据 BeanName 获取 BeanDefinition
     * */
    BeanDefinition getBeanDefinition(String name);

    /**
     * 判断 BeanDefinition 是否存在
     * */
    boolean containsBeanDefinition(String name);
}
