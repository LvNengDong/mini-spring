package com.minis.core.env;

/**
 * @Author lnd
 * @Description
 *      Environment 继承 PropertyResoulver 接口，用于获取属性
 *
 *      所有的 ApplicationContext 都实现了 Environment 接口
 * @Date 2024/3/1 23:22
 */
public interface Environment extends PropertyResolver {
    String[] getActiveProfiles();
    String[] getDefaultProfiles();
    boolean acceptsProfiles(String... profiles);
}