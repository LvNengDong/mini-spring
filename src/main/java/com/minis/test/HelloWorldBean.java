package com.minis.test;

import com.minis.beans.factory.annotation.Autowired;
import com.minis.test.service.IAction;
import com.minis.web.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @Autowired
    IAction action;

    @RequestMapping("/testaop")
    public void doTestAop(HttpServletRequest request, HttpServletResponse response) {
        action.doAction();
        String str = "test aop, hello world!";
        try {
            response.getWriter().write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
