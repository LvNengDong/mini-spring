package com.minis.test.bean;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:07
 */
public class AiGuoServiceImpl implements XService{

    private String country;

    @Override
    public void sayWhat() {
        System.out.println("好好学习，天天向上");
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
