package com.minis;

/**
 * @Author lnd
 * @Description 集合了多个 XxxBeanFactory 的特性
 * @Date 2024/3/1 22:45
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
