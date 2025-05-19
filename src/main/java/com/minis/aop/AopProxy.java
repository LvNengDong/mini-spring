package com.minis.aop;

/**
 * @Author lnd
 * @Description
 *  Spring 作为一个雄心勃勃的框架，自然不会把自己局限于 JDK 提供的动态代理一个技术上，
 *  所以，它再次进行了包装，提供了 AopProxy 的概念，JDK 只是其中的一种实现。
 * @Date 2025/5/19 17:17
 */
public interface AopProxy {
    Object getProxy();
}
