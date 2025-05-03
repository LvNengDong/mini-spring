package com.minis.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:13
 */
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest request) throws Exception;
}
