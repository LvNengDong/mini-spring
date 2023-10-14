package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.test.bean.XService;
import com.minis.test.utils.printLog.FormatUtil;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:09
 */
public class Test {
    public static void main(String[] args) throws BeansException {
        FormatUtil.start();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        XService aiguo = (XService) context.getBean("aiguo");
        aiguo.sayWhat();
        FormatUtil.split();
        XService wangcai = (XService) context.getBean("wangcai");
        wangcai.sayWhat();
        FormatUtil.end();

    }


}

