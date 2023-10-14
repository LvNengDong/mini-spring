package com.minis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lnd
 * @Description BeanDefinition直接映射XML文件中bean的定义
 * @Date 2023/4/22 22:43
 */
@AllArgsConstructor
@Data
public class BeanDefinition {
    private String id;
    private String className;
}
