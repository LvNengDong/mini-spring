package com.minis.context;


/**
 * @Author lnd
 * @Description 为了监控容器的启动状态，我们要增加事件监听。
 * @Date 2023/10/15 10:28
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     * */
    void publishEvent(ApplicationEvent event);

    /**
     * 添加事件监听器
     * */
    void addApplicationListener(ApplicationListener listener);

}
