package com.minis.factory;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Maps;
import com.minis.DefaultSingletonBeanRegistry;
import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.factory.BeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 *      Q：SimpleBeanFactory 继承 DefaultSingletonBeanRegistry 的原因是什么？
 *         A：确保我们通过 SimpleBeanFactory 创建的 Bean 默认就是单例的。
 *            DefaultSingletonBeanRegistry 通过 SimpleBeanFactory 对外暴露服务，而在 Spring 中默认创建的bean就是单例的，
 *            正是通过 `SimpleBeanFactory extends DefaultSingletonBeanRegistry` 来实现的
 * @Date 2023/10/14 14:12
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();

    //private List beanNames = new ArrayList<>();
    /**
     * Bean容器
     *  因为 DefaultSingletonBeanRegistry 也维护了一个 singletons，所以这里可以直接使用其父类中的，
     *  同理，beanNames 也是类似的
     */
    // private Map<String, Object> singletons = new HashMap<>();

    /**
     * Part6、保存 bean 到 bean 容器中
     *
     *      1、保存 bean 到 map（singletons） 中
     *      2、获取 bean
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = singletons.get(beanName);
        //如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        if (singleton == null) {
            // 获取Bean的定义
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                throw new BeansException(beanName + " 对应的beanDefinition不存在");
            } else {
                try {
                    /* Part4、创建实例Bean */
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (Exception e) {
                    log.error("根据beanDefinition创建Bean实例异常,beanDefinition:{}", JSON.toJSONString(beanDefinition), e);
                    throw new BeansException(e.toString());
                }
                /* Part5、保存 bean 到 bean 容器中*/
                singletons.put(beanDefinition.getId(), singleton);
            }
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getId();
        beanDefinitionMap.put(beanName, beanDefinition);
        beanNames.add(beanName);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        // 使用 DefaultSingletonBeanRegistry 的实现
        super.containsSingleton(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        // 使用 DefaultSingletonBeanRegistry 的实现
        return super.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class getType(String name) {
        return null;
    }
}
