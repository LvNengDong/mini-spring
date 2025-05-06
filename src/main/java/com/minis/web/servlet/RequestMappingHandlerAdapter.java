package com.minis.web.servlet;

import com.minis.web.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:13
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {

    WebApplicationContext wac;

    WebBindingInitializer webBindingInitializer;

    HttpMessageConverter messageConverter;


    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    protected void invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        Object[] paramObjs = new Object[parameters.length];
        int i = 0;
        for (Parameter parameter : parameters) {
            Object paramObj = parameter.getType().newInstance();
            WebDataBinder webDataBinder = binderFactory.createBinder(request, paramObj, parameter.getName());
            webDataBinder.bind(request);
            paramObjs[i] = paramObj;
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();
        Object objResult = invocableMethod.invoke(handlerMethod.getBean(), paramObjs);
        response.getWriter().append(objResult.toString());
    }
}
