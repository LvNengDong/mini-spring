package com.minis.context;

import java.util.EventObject;

/**
 * @Author lnd
 * @Description 事件对象
 * @Date 2023/10/15 10:29
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    protected String msg;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
        this.msg = source.toString();
    }
}
