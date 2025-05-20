package com.minis.context;

import java.util.EventObject;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 10:29
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    public ApplicationEvent(Object source) {
        super(source);
    }
}
