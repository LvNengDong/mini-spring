package com.minis;

import com.google.common.collect.Lists;
import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;

import java.util.List;
import java.util.Objects;

/**
 * @Author lnd
 * @Description 这个 BeanFactory 就是专为 Autowired 注入的 Bean 准备的
 * @Date 2024/2/26 23:52
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
