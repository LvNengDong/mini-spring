package com.minis.core;

import java.util.EventObject;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 23:52
 */
public class ApplicationEvent extends EventObject {
    public static final long serialVersionUID = 1L;
    public ApplicationEvent(Object arg0) { super(arg0); }
}
