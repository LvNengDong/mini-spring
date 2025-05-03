package com.minis.web.servlet;

import com.minis.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:13
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

    WebApplicationContext wac;

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        handleInternal(request, response, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        try {
            Method method = handler.getMethod();
            Object obj = handler.getBean();
            Object objResult = method.invoke(obj);
            response.getWriter().append(objResult.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
