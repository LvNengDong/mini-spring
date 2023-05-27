package com.minis.context;

import com.minis.context.ApplicationEvent;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 23:51
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
