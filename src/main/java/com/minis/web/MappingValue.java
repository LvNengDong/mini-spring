package com.minis.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lnd
 * @Description
 *  作用：minisMVC-servlet.xml 配置文件 beans.bean 标签的内存映射。
 *  uri、clz 与 method，分别与 minisMVC-servlet.xml 配置文件中标签的属性 id、class 与 value 对应
 * @Date 2024/4/17 15:02
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class MappingValue {
    String uri;
    String clz;
    String method;
}
