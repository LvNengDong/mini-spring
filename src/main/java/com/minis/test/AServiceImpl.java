package com.minis.test;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:07
 */
public class AServiceImpl implements AService{

    private String property1;

    public void setProperty1(String property1) {
        this.property1 = property1;
    }
    @Override
    public void sayHello(String property) {
        System.out.println("a service 1 say hello");
    }
}
