package com.minis.context;


import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author lnd
 * @Description
 * @Date 2024/3/3 14:24
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    List<ApplicationListener> listeners = Lists.newArrayList();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
