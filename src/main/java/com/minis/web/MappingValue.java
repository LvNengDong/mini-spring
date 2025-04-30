package com.minis.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lnd
 * @Description uri、clz 与 method，分别与 minisMVC-servlet.xml 中标签的属性 id、class 与 value 对应
 * 例如：<bean id="hello" class="com.minis.web.HelloController" value="/hello"/>
 * 则 uri = "/hello"，clz = "com.minis.web.HelloController"，method = "hello"
 * 其中 method 为 HelloController 类中的方法名，用于反射调用
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
