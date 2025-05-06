package com.minis.web;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/6 08:44
 */
public interface HttpMessageConverter {
    void write(Object obj, HttpServletResponse response) throws Exception;
}
