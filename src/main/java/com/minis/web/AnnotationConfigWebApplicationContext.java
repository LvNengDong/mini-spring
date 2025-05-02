package com.minis.web;

import com.minis.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/2 18:06
 */
public class AnnotationConfigWebApplicationContext
        extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
