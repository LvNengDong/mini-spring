package com.minis;

import java.util.EventObject;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 10:29
 */
public class ApplicationEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
