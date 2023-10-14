package com.minis.test;

import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:07
 */
@Data
public class AServiceImpl implements AService {

    private String name;
    private int level;
    private String property1;
    private String property2;

    public AServiceImpl() {
    }

    public AServiceImpl(String name, int level) {
        this.name = name;
        this.level = level;
        System.out.println(this.name + "," + this.level);
    }

    @Override
    public void sayHello(String property) {
        System.out.println("a service 1 say hello");
    }
}
