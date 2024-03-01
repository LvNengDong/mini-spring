package com.minis.test.utils.printLog;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

/**
 * @Author lnd
 * @Description  格式化
 * @Date 2023/10/14 11:38
 */
public class FormatUtil {
    public static void start() {
        System.out.println("==========start=============");
    }

    public static void split() {
        System.out.println();
        System.out.println(">>>>>>>>>>>分割线>>>>>>>>>>>>");
        System.out.println();
    }

    public static void end() {
        System.out.println("==========end=============");
    }

    public static String prettyJson(Object object) {
        return JSON.toJSONString(object, JSONWriter.Feature.PrettyFormat);
    }
}
