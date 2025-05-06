package com.minis.beans;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/4 17:35
 */
public interface PropertyEditor {
    void setAsText(String text);

    void setValue(Object value);

    Object getValue();

    Object getAsText();
}
