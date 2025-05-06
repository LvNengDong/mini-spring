package com.minis.beans;

import com.sun.xml.internal.txw2.TXW;
import org.apache.commons.lang3.StringUtils;

import java.text.NumberFormat;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/4 17:36
 */
public class CustomNumberEditor implements PropertyEditor {

    private Class<? extends Number> numberClass;    // 数据类型
    private NumberFormat numberFormat;               // 格式化器
    private boolean allowEmpty;                      // 是否允许为空
    private Object value;                            // 数据值

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    // 将一个字符串转换成number类型
    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && StringUtils.isEmpty(text)) {
            setValue(null);
        } else if (this.numberFormat != null) {
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            this.value = (NumberUtils.convertNumberToTargetClass((Number)value, this.numberClass));
        } else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    // 将 Number 表示成格式化串
    @Override
    public Object getAsText() {
        Object value = this.value;
        if (value == null) {
            return "";
        }
        if (this.numberFormat != null) {
            return this.numberFormat.format(value);
        } else {
            return value.toString();
        }
    }
}
