package com.minis.web;

import com.minis.beans.BeansException;
import com.minis.beans.factory.ListableBeanFactory;
import com.minis.beans.factory.config.BeanFactoryPostProcessor;
import com.minis.beans.factory.config.ConfigurableBeanFactory;
import com.minis.beans.factory.config.ConfigurableListableBeanFactory;
import com.minis.context.ApplicationEventPublisher;
import com.minis.core.env.Environment;
import com.minis.core.env.EnvironmentCapable;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/2 17:38
 */
public interface ApplicationContext
        extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory, ApplicationEventPublisher {
    String getApplicationName();
    long getStartupDate();
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
    void setEnvironment(Environment environment);
    Environment getEnvironment();
    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);
    void refresh() throws BeansException, IllegalStateException;
    void close();
    boolean isActive();
}
