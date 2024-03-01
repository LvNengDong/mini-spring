package com.minis;

import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

import java.util.Map;

/**
 * @Author lnd
 * @Description
 *      接口只代码其实现类应该有接口中定义的这些特性，
 *      但是其实现类除了有这些特性，还可以实现其它BeanFactory接口具备更多的特性
 *
 *      ListableBeanFactory 可以将 Factory 内部管理的 Bean 作为一个集合来对待，
 *      拥有获取 Bean 的数量，得到所有 Bean 的名字，按照某个类型获取 Bean 列表等功能
 * @Date 2024/3/1 22:45
 */
public interface ListableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);
    int getBeanDefinitionCount();
    String[] getBeanDefinitionNames();
    String[] getBeanNamesForType(Class<?> type);
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;
}
