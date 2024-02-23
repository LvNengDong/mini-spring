package com.minis.factory;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minis.BeanDefinitionRegistry;
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
 *            我们需要将 SimpleBeanFactory 中关于创建 Bean 的直接方法，而是间接使用 DefaultSingletonBeanRegistry 中创建 Bean 的方法，
 *            以确保得到单例 bean。
 *            DefaultSingletonBeanRegistry 通过 SimpleBeanFactory 对外暴露服务，而在 Spring 中默认创建的bean就是单例的，
 *            正是通过 `SimpleBeanFactory extends DefaultSingletonBeanRegistry` 来实现的
 * @Date 2023/10/14 14:12
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    // BeanDefinition不涉及单例多例的概念，所以放在比较通用的 SimpleBeanFactory 类中即可
    private Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();
    private List<String> beanDefinitionNames = Lists.newArrayList();

    /**
     * Bean容器
     *  因为 DefaultSingletonBeanRegistry 维护了一个单例 singletons，所以这里直接使用其父类中的，确保默认情况下创建的是单例 bean
     *  同理，beanNames 也是类似的
     */
    // private Map<String, Object> singletons = new HashMap<>();
    // private List beanNames = new ArrayList<>();

    /**
     * parentClass ： BeanFactory
     * Part6、保存 bean 到 bean 容器中
     *
     *      1、保存 bean 到 map（singletons） 中
     *      2、获取 bean
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接拿Bean实例
        Object singleton = super.getSingleton(beanName);
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
                /* Part5、保存 bean 到 bean 容器中(走默认的单例实现)*/
                super.registerSingleton(beanName, singleton);
            }
        }
        return singleton;
    }


    /**
     * parentClass ： BeanFactory
     * */
    @Override
    public void registerBean(String beanName, Object obj) {
        // 使用 DefaultSingletonBeanRegistry 的实现
        super.registerSingleton(beanName, obj);
    }

    /**
     * parentClass ： BeanFactory
     * */
    @Override
    public boolean containsBean(String beanName) {
        // 使用 DefaultSingletonBeanRegistry 的实现
        return super.containsSingleton(beanName);
    }

    /**
     * parentClass ： BeanFactory
     * */
    @Override
    public boolean isSingleton(String name) {
        return beanDefinitionMap.get(name).isSingleton();
    }

    /**
     * parentClass ： BeanFactory
     * */
    @Override
    public boolean isPrototype(String name) {
        return beanDefinitionMap.get(name).isPrototype();
    }
    /**
     * parentClass ： BeanFactory
     * */
    @Override
    public Class getType(String name) {
        return null;
    }

    /**
     * parentClass ： BeanDefinitionRegistry
     * 作用： 注册 BeanDefinition
     * 备注： 目前这个方法不再是继承自 BeanFactory 接口中的方法了，而是继承自 BeanDefinitionRegistry 中的方法
     * */
    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
        if (!beanDefinition.isLazyInit()) {
            log.info("非懒加载的bean，立即创建bean实例。beanName:{}",name);
            try {
                getBean(name);
            } catch (BeansException e) {
                log.info("创建bean异常 beanName:{}", name);
            }
        }
    }

    /*
    * parentClass ： BeanDefinitionRegistry
    * */
    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    /*
     * parentClass ： BeanDefinitionRegistry
     * */
    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    /*
     * parentClass ： BeanDefinitionRegistry
     * */
    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
