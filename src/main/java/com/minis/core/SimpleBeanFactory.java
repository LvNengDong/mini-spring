package com.minis.core;

import com.google.common.collect.Maps;
import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.beans.DefaultSingletonBeanRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 19:47
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    //private List<BeanDefinition> beanDefinitions = Lists.newArrayList();
    //private List<String> beanNames = Lists.newArrayList();
    //public Map<String, Object> sigletons = Maps.newHashMap();

    private Map<String, BeanDefinition> beanDefinitions = Maps.newConcurrentMap();

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 使用 DefaultSingletonBeanRegistry 中的方法管理单例bean
        Object singleton = this.getSingleton(beanName);
        // 如果此时还没有这个实例，则获取它的定义来创建实例
        if (Objects.isNull(singleton)) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (Objects.isNull(beanDefinition)) {
                log.info("传入的beanName未被Spring管理 beanName:{}", beanName);
                throw new BeansException("传入的beanName未被管理：" + beanName);
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                log.error("通过反射创建对象实例出错 beanName:{} className", beanName, beanDefinition.getClassName(), e);
                throw new RuntimeException(e);
            }
            // 注册Bean实例
            this.registerSingleton(beanName, singleton);
        }
        return singleton;
    }


    public void registerBeanDefinition(BeanDefinition beanDefinition) { this.beanDefinitions.put(beanDefinition.getId(), beanDefinition); }

    @Override
    public Boolean containsBean(String name) {
        // 使用DefaultSingletonBeanRegistry的实现
        return containsSingleton(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        // 使用DefaultSingletonBeanRegistry的实现
        this.registerSingleton(beanName, obj);
    }
}
