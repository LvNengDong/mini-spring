package com.minis.aop;

import com.minis.beans.factory.FactoryBean;
import com.minis.util.ClassUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 17:07
 */
public class ProxyFactoryBean implements FactoryBean<Object> {
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();

    private Object singletonInstance;

    private AopProxyFactory aopProxyFactory;

    @Getter @Setter
    private Object target;

    @Getter @Setter
    private String[] interceptorNames;

    @Getter
    private String[] targetName;



    @Override
    public Object getObject() throws Exception {
        return getSingletonInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    /*
     * 获取代理对象
     * */
    private synchronized Object getSingletonInstance() {
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    private AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target);
    }

    private AopProxyFactory getAopProxyFactory() {
        return this.aopProxyFactory;
    }

    /**
     * 生成代理对象
     */
    private Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }
}
