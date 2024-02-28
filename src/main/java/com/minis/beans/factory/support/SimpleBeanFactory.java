package com.minis.beans.factory.support;

import com.minis.beans.factory.BeanFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author lnd
 * @Description Q：SimpleBeanFactory 继承 DefaultSingletonBeanRegistry 的原因是什么？
 * A：确保我们通过 SimpleBeanFactory 创建的 Bean 默认就是单例的。
 * 我们需要将 SimpleBeanFactory 中关于创建 Bean 的直接方法，而是间接使用 DefaultSingletonBeanRegistry 中创建 Bean 的方法，
 * 以确保得到单例 bean。
 * DefaultSingletonBeanRegistry 通过 SimpleBeanFactory 对外暴露服务，而在 Spring 中默认创建的bean就是单例的，
 * 正是通过 `SimpleBeanFactory extends DefaultSingletonBeanRegistry` 来实现的
 * @Date 2023/10/14 14:12
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    /**
     * Bean容器
     *  因为 DefaultSingletonBeanRegistry 维护了一个单例 singletons，所以这里直接使用其父类中的，确保默认情况下创建的是单例 bean
     *  同理，beanNames 也是类似的
     */
    // private Map<String, Object> singletons = new HashMap<>();
    // private List beanNames = new ArrayList<>();
}
