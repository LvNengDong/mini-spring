package com.minis.beans;

/**
 * @Author lnd
 * @Description 存放BeanDefinition的仓库，用于管理BeanDefinition
 * @Date 2023/5/28 00:01
 */
public interface BeanDefinitionRegistry {
    /** 注册bd */
    void registerBeanDefinition(String name, BeanDefinition bd);

    /** 删除bd */
    void removeBeanDefinition(String name);

    /** 获取单个bd */
    BeanDefinition getBeanDefinition(String name);

    /** 判断bd是否存在 */
    boolean containsBeanDefinition(String name);
}
