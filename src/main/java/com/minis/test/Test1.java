package com.minis.test;

import com.minis.core.ClassPathXmlApplicationContext;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:09
 */
public class Test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aservice = (AService) context.getBean("aservice");
        AService aservice2 = (AService) context.getBean("aservice2");
        aservice2.sayHello();
    }
}
