package com.minis.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 19:47
 */
@Slf4j
public class SimpleBeanFactory implements BeanFactory {
    private List<BeanDefinition> beanDefinitions = Lists.newArrayList();
    private List<String> beanNames = Lists.newArrayList();
    public Map<String, Object> sigletons = Maps.newHashMap();

    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接获取bean实例
        Object singleton = sigletons.get(beanName);
        // 如果此时还没有这个实例，则获取它的定义来创建实例
        if (Objects.isNull(singleton)) {
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                log.info("传入的beanName未被Spring管理 beanName:{}", beanName);
                throw new BeansException("传入的beanName未被管理：" + beanName);
            } else {
                // 获取Bean的定义
                /*List是有序的，所以beanDefinitions和beanNames中元素和名字的顺序是一一对应的*/
                BeanDefinition beanDefinition = beanDefinitions.get(i);
                try {
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    log.error("通过反射创建对象实例出错 beanName:{} className", beanName, beanDefinition.getClassName(), e);
                    throw new RuntimeException(e);
                }
                // 注册Bean实例
                sigletons.put(beanDefinition.getId(), sigletons);
            }
        }
        return singleton;
    }

    /**
     *
     * @param beanDefinition 读取XML配置文件，映射成BeanDefinition，作为这个方法的入参
     * @throws BeansException
     */
    @Deprecated
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
