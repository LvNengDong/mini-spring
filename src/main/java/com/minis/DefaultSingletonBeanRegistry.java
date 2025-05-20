package com.minis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lnd
 * @Description SingletonBeanRegistry 的默认的实现类，提供接口对应的能力
 * @Date 2023/10/15 09:35
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final List<String> singletonBeanNames = new ArrayList<>();
    private final Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) { // 线程安全
            singletons.put(beanName, singletonObject);
            this.singletonBeanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonBeanNames.contains(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.singletonBeanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.singletonBeanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }

}
