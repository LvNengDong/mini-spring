package com.minis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 11:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyValue {

    private String type;
    private String name;
    private Object value;
    /**
     * 判断属性是引用类型还是普通的值类型
     */
    private boolean isRef;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
