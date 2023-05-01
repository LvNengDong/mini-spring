package com.minis.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 23:27
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /*容器中存放的所有单例bean的名称的列表*/
    List<String> beanNames = Lists.newArrayList();

    /*容器中存放的所有单例bean实例的map*/
    Map<String, Object> singletons = Maps.newConcurrentMap();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            this.singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons){
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }
}
