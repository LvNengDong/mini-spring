package com.minis;

/**
 * @Author lnd
 * @Description
 *      单例的注册、获取、判断是否存在、获取所有单例bean的名字
 *
 *      SingletonBeanRegistry 可以看做是对 BeanFactory 部分功能的抽取，它把 BeanFactory 针对单例 Bean 的一系列操作提取出来
 *      单独形成一个类，让 BeanFactory 可以更好的完成顶层抽象，而对于单例 Bean 的操作就通过 SingletonBeanRegistry 来实现。通过
 *      SingletonBeanRegistry 和 BeanFactory 组合来实现想要的功能。
 * @Date 2023/10/15 09:33
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);
    Object getSingleton(String beanName);
    boolean containsSingleton(String beanName);
    String[] getSingletonNames();
}
