package com.minis.beans;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/4 17:50
 */
public class StringEditor implements PropertyEditor {

    private Class<String> strClass;
    private String format;
    private boolean allowEmpty;
    private Object value;

    public StringEditor(Class<String> strClass, boolean allowEmpty) {
        this(strClass, "", allowEmpty);
    }

    public StringEditor(Class<String> strClass, String format, boolean allowEmpty) {
        this.strClass = strClass;
        this.format = format;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getAsText() {
        return value.toString();
    }
}
