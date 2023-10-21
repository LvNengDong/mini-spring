package com.minis;

import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 11:19
 */
@Data
public class ArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ArgumentValue(Object value, String type) {
        this.value = value;
        this.type = type;
    }

    public ArgumentValue(Object value, String type, String name) {
        this.value = value;
        this.type = type;
        this.name = name;
    }
}
