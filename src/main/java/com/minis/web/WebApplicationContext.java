package com.minis.web;

import javax.servlet.ServletContext;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/2 17:37
 */
public interface WebApplicationContext extends ApplicationContext {

    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);

}
