package com.minis.context;

import com.minis.context.ApplicationEvent;

import java.util.EventListener;

/**
 * @Author lnd
 * @Description
 * @Date 2024/3/3 14:21
 */
public class ApplicationListener implements EventListener {
    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
