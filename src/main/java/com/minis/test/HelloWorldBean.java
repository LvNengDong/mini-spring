package com.minis.test;

import com.minis.web.RequestMapping;

/**
 * @Author lnd
 * @Description
 * @Date 2024/4/17 15:36
 */
public class HelloWorldBean {
    @RequestMapping("/test")
    public String doGet() {
        return "hello world for doGet!";
    }

    public String doPost() {
        return "hello world for doPost!";
    }
}
