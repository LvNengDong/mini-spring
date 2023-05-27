package com.minis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/27 23:23
 */
@AllArgsConstructor
@Data
public class PropertyValue {
    private String name;
    private Object value;
}
