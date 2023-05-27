package com.minis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/27 23:23
 */
@AllArgsConstructor
@Data
public class ArgumentValue {
    private Object value;
    private String type;
    private String name;

    public ArgumentValue(Object value, String name) {
        this.value = value;
        this.name = name;
    }
}
