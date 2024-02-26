package com.minis.beans.factory.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/15 11:19
 */
@Data
@AllArgsConstructor
public class ConstructorArgumentValue {

    private String type;
    private String name;
    private Object value;
}
