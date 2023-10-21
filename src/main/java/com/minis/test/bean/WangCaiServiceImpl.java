package com.minis.test.bean;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:25
 */
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
