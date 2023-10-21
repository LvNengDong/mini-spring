package com.minis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lnd
 * @Description 提供默认的实现类。这也是从 Spring 里学的方法，它作为一个框架并不会把代码写死，所以这里面的很多实现类都是默认的，默认是什么意思呢？
 * 就是我们可以去替换，不用这些默认的类也是可以的。
 * @Date 2023/10/15 09:35
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // 容器中存放所有bean的名称的列表
    protected List<String> beanNames = new ArrayList<>();

    // 容器中存放所有bean实例的map
    protected Map<String, Object> singletons = new ConcurrentHashMap<>(256);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletons) {
            singletons.put(beanName, singletonObject);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.beanNames.contains(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return (String[]) this.beanNames.toArray();
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletons) {
            this.beanNames.remove(beanName);
            this.singletons.remove(beanName);
        }
    }

}
