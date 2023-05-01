package com.minis.core;

import java.util.Iterator;

/**
 * @Author lnd
 * @Description 我们把外部的配置信息都当成 Resource（资源）来进行抽象
 *
 * @Date 2023/5/1 19:16
 */
public interface Resource extends Iterator<Object> {
}


/*
1、为什么要继承Iterator
    Java中实现Iterator接口的作用是用于遍历集合类中的元素，可以在不暴露集合内部实现细节的情况下，
    对集合进行迭代访问。Iterator接口提供了一些方法，例如next()用于获取下一个元素，hasNext()
    用于判断是否还有下一个元素等，使得遍历集合变得更加方便和灵活。同时，实现Iterator接口的类可以
    使用增强for循环进行遍历，增强for循环本质上也是通过调用Iterator接口的方法来实现遍历的。
*/