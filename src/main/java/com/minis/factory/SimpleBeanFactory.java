package com.minis.factory;

import com.alibaba.fastjson2.JSON;
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
 * @Date 2023/10/14 14:12
 */
@Slf4j
public class SimpleBeanFactory implements BeanFactory {
    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private List<String> beanNames = new ArrayList<>();

    /**
     * Bean容器
     */
    private Map<String, Object> singletons = new HashMap<>();

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
            int i = beanNames.indexOf(beanName);
            if (i == -1) {
                throw new BeansException(beanName + " 对应的beanDefinition不存在");
            } else {
                // 获取Bean的定义
                BeanDefinition beanDefinition = beanDefinitions.get(i);
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
        beanDefinitions.add(beanDefinition);
        beanNames.add(beanName);
    }
}
