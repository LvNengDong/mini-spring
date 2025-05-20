package com.minis.factory;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minis.BeanDefinitionRegistry;
import com.minis.DefaultSingletonBeanRegistry;
import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.factory.BeanFactory;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 *      Q：SimpleBeanFactory 继承 DefaultSingletonBeanRegistry 的原因是什么？
 *          A：BeanFactory 中关于 bean 注册、获取等方法实际是由 DefaultSingletonBeanRegistry 提供的，
 *          SimpleBeanFactory 起到了类似网关的效果，提供统一的对外服务，在内部通过调用不同的实现类完成功能。
 * @Date 2023/10/14 14:12
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    // BeanDefinition 不涉及单例多例的概念，所以放在比较通用的 SimpleBeanFactory 类中即可
    private final Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();
    private final List<String> beanDefinitionNames = Lists.newArrayList();

    /**
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
                    /* 创建实例Bean */
                    singleton = Class.forName(beanDefinition.getClassName()).newInstance();
                } catch (Exception e) {
                    log.error("根据beanDefinition创建Bean实例异常,beanDefinition:{}", JSON.toJSONString(beanDefinition), e);
                    throw new BeansException(e.toString());
                }
                /* 保存 bean 到 bean 容器中(走默认的单例实现)*/
                super.registerSingleton(beanName, singleton);
            }
        }
        return singleton;
    }


    @Override
    public void registerBean(String beanName, Object obj) {
        super.registerSingleton(beanName, obj);
    }

    @Override
    public boolean containsBean(String beanName) {
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

    /**
     * 注册 BeanDefinition
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
