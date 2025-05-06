package com.minis.web;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/4 20:10
 */
public class WebDataBinderFactory {
    public WebDataBinder createBinder(HttpServletRequest request, Object target, String objectName) {
        WebDataBinder wdb = new WebDataBinder(target, objectName);
        initBinder(wdb, request);
        return wdb;
    }

    private void initBinder(WebDataBinder dataBinder, HttpServletRequest request) {

    }
}
