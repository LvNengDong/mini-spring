package com.minis.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:14
 */
public interface HandlerAdapter {
    void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
