package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:09
 */
public class Test1 {
    public static void main(String[] args) throws BeansException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        AService aservice = (AService) context.getBean("aservice");
        aservice.sayHello("sss");
    }
}
