package com.minis.beans;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minis.beans.BeanDefinition;
import com.minis.beans.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.beans.DefaultSingletonBeanRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author lnd
 * @Description SimpleBeanFactory既实现了BeanFactory，又实现了BeanDefinitionRegistry，这样BeanFactory既是一个工厂，同时也是一个bd的藏句
 * @Date 2023/5/1 19:47
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = Maps.newConcurrentMap();
    private List<String> beanDefinitionNames = Lists.newArrayList();

    /**
     * 默认获取单例bean
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 使用 DefaultSingletonBeanRegistry 中的方法获取单例bean
        Object singleton = this.getSingleton(beanName);
        if (Objects.isNull(singleton)) {
            // 如果此时还没有这个实例，则获取它的定义来创建实例
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (Objects.isNull(beanDefinition)) {
                log.info("传入的beanName未被Spring管理 beanName:{}", beanName);
                throw new BeansException("传入的beanName未被管理：" + beanName);
            }
            singleton = createBean(beanDefinition);
            // 注册单例Bean
            this.registerSingleton(beanName, singleton);
        }
        return singleton;
    }

    /**
     * 根据beanDefinition创建bean
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Object obj = null;
        Class<?> clz = null;
        try {
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (Objects.nonNull(argumentValues)) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Class<?>[] paramValues = new Class<?>[argumentValues.getArgumentCount()];
            }
            singleton = clz.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            log.error("通过反射创建对象实例出错 beanName:{} className", beanName, beanDefinition.getClassName(), e);
            throw new RuntimeException(e);
        }
        return singleton;
    }

    /**
     * 是否存在单例Bean
     */
    @Override
    public Boolean containsBean(String beanName) {
        // 使用DefaultSingletonBeanRegistry的实现
        return this.containsSingleton(beanName);
    }

    /**
     * 注册单例bean
     */
    @Override
    public void registerBean(String beanName, Object obj) {
        // 使用DefaultSingletonBeanRegistry的实现
        this.registerSingleton(beanName, obj);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                log.info("对于非懒加载的bean，在注册BeanDefinition的时候就创建bean");
                getBean(name);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
