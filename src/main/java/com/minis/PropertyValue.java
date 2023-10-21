package com.minis;

import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 11:20
 */
@Data
public class PropertyValue {
    private Object value;
    private String type;
    private String name;

    public PropertyValue(String type, Object value) {
        this.value = value;
        this.type = type;
    }
}
