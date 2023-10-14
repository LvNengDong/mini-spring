package com.minis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @Author lnd
 * @Description 构造方法注入
 * @Date 2023/5/27 23:23
 */
@AllArgsConstructor
@Data
public class ArgumentValue {
    private String type;
    private String name;
    private Object value;
}
