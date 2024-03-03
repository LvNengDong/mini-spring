package com.minis.context;

import com.minis.BeanFactoryPostProcessor;
import com.minis.BeanPostProcessor;
import com.minis.ConfigurableListableBeanFactory;
import com.minis.beans.BeansException;
import com.minis.core.env.Environment;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author lnd
 * @Description
 * @Date 2024/3/3 14:59
 */
@Slf4j
public abstract class AbstractApplicationContext implements ApplicationContext {

    /*
     * 使一些容器整体所需要的属性有个地方存储访问
     * */
    private Environment environment;

    @Override
    public Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /*
     * 管理BeanFactory后处理器
     * */
    @Getter
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    private long startupDate;
    private final AtomicBoolean active = new AtomicBoolean();
    private final AtomicBoolean closed = new AtomicBoolean();

    @Getter
    @Setter
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 实现 BeanFactory 的功能
     * */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }
    /**
     * 实现 BeanFactory 的功能
     * */
    @Override
    public boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }
    /**
     * 实现 BeanFactory 的功能
     * */
    @Override
    public boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }
    /**
     * 实现 BeanFactory 的功能
     * */
    @Override
    public boolean isPrototype(String name) {
        return getBeanFactory().isPrototype(name);
    }
    /**
     * 实现 BeanFactory 的功能
     * */
    @Override
    public Class<?> getType(String name) {
        return getBeanFactory().getType(name);
    }


    public void refresh() throws BeansException, IllegalStateException {
        postProcessBeanFactory(getBeanFactory());

        log.info("将BeanPostProcessor注册到BeanFactory中");
        registerBeanPostProcessors(getBeanFactory());

        initApplicationEventPublisher();

        log.info("刷新容器中的所有Bean");
        onRefresh();

        registerListeners();

        finishRefresh();
    }

    protected abstract void finishRefresh();

    /**
     * 注册监听者
     */
    protected abstract void registerListeners();

    protected abstract void onRefresh();

    /**
     * 初始化事件发布者
     */
    protected abstract void initApplicationEventPublisher();

    protected abstract void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory);

    protected abstract void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);

    /**
     * 实现 SingletonBeanRegistry 的功能
     * */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        getBeanFactory().registerSingleton(beanName, singletonObject);
    }
    /**
     * 实现 SingletonBeanRegistry 的功能
     * */
    @Override
    public Object getSingleton(String beanName) {
        return getBeanFactory().getSingleton(beanName);
    }
    /**
     * 实现 SingletonBeanRegistry 的功能
     * */
    @Override
    public boolean containsSingleton(String beanName) {
        return getBeanFactory().containsSingleton(beanName);
    }
    /**
     * 实现 SingletonBeanRegistry 的功能
     * */
    @Override
    public String[] getSingletonNames() {
        return getBeanFactory().getSingletonNames();
    }

    /**
     * 实现 ListableBeanFactory 的功能
     * */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }
    /**
     * 实现 ListableBeanFactory 的功能
     * */
    @Override
    public int getBeanDefinitionCount() {
        return getBeanFactory().getBeanDefinitionCount();
    }
    /**
     * 实现 ListableBeanFactory 的功能
     * */
    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 实现 ListableBeanFactory 的功能
     * */
    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }
    /**
     * 实现 ListableBeanFactory 的功能
     * */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 实现 ConfigurableBeanFactory 的功能
     * */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        getBeanFactory().addBeanPostProcessor(beanPostProcessor);

    }
    /**
     * 实现 ConfigurableBeanFactory 的功能
     * */
    @Override
    public int getBeanPostProcessorCount() {
        return getBeanFactory().getBeanPostProcessorCount();
    }
    /**
     * 实现 ConfigurableBeanFactory 的功能
     * */
    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {
        getBeanFactory().registerDependentBean(beanName, dependentBeanName);
    }
    /**
     * 实现 ConfigurableBeanFactory 的功能
     * */
    @Override
    public String[] getDependentBeans(String beanName) {
        return getBeanFactory().getDependentBeans(beanName);
    }
    /**
     * 实现 ConfigurableBeanFactory 的功能
     * */
    @Override
    public String[] getDependenciesForBean(String beanName) {
        return getBeanFactory().getDependenciesForBean(beanName);
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public abstract ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    @Override
    public void close() {

    }

    @Override
    public boolean isActive() {
        return true;
    }
}
