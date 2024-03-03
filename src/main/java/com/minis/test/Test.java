package com.minis.test;

import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlApplicationContext;
import com.minis.test.bean.Desk;
import com.minis.test.bean.Stool;
import com.minis.test.bean.XService;
import com.minis.test.utils.printLog.FormatUtil;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 23:09
 */
public class Test {
    public static void main(String[] args) throws BeansException {
        // 目前 isRefresh 参数必须传 true，因为只有这样才会将 BeanPostProcessor 注册到 BeanFactory 中
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml", true);
        FormatUtil.start();
        XService aiguo = (XService) context.getBean("aiguo");
        System.out.println(FormatUtil.prettyJson(aiguo));
        FormatUtil.split();
        XService wangcai = (XService) context.getBean("wangcai");
        System.out.println(FormatUtil.prettyJson(wangcai));

        Desk desk = (Desk)context.getBean("desk");
        System.out.println(FormatUtil.prettyJson(desk));
        Stool stool = desk.getStool();
        System.out.println(FormatUtil.prettyJson(stool));
        System.out.println(desk);
        FormatUtil.end();

    }
}

