package com.minis.beans.factory.support;

import com.minis.beans.BeansException;
import com.minis.beans.factory.FactoryBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 16:45
 */
@Slf4j
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        try {
            Object object = doGetObjectFromFactoryBean(factoryBean, beanName);
            return postProcessObjectFromFactoryBean(object, beanName);
        } catch (BeansException e) {
            log.error("FactoryBean threw exception on object[{}] creation", beanName, e);
            return null;
        }
    }

    /**
     * 从 Factory Bean 中获取内部包含的对象
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName) {
        try {
            // 从工厂中获取对象
            return factory.getObject();
        } catch (Exception e) {
            log.error("FactoryBean threw exception on object[{}] creation", beanName, e);
            return null;
        }
    }

    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
        return object;
    }
}
