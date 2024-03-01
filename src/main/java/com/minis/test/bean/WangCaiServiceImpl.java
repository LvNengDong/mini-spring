package com.minis.test.bean;

import lombok.Getter;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:25
 */
@Getter
public class WangCaiServiceImpl implements XService{

    private String gender;
    private int age;

    public WangCaiServiceImpl(String gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    @Override
    public void sayWhat() {
        System.out.println("汪汪汪");
    }
}
