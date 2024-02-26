package com.minis;

import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.support.BeanDefinitionRegistry;
import com.minis.beans.factory.support.DefaultSingletonBeanRegistry;

/**
 * @Author lnd
 * @Description 抽取 SimpleBeanFactory 和 AutowireCapableBeanFactory 中公共的部分到当前类中
 * @Date 2024/2/26 23:53
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
}
