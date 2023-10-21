package com.minis;

import com.apple.eawt.ApplicationEvent;

/**
 * @Author lnd
 * @Description 为了监控容器的启动状态，我们要增加事件监听。
 * @Date 2023/10/15 10:28
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);

}
