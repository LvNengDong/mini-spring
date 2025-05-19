package com.minis.beans.factory;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/19 16:43
 */
public interface FactoryBean<T> {
    /**
     * 从 Factory Bean 中获取内部包含的对象
     */
    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
