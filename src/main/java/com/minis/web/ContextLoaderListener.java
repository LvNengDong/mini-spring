package com.minis.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/2 17:32
 */
@AllArgsConstructor
@NoArgsConstructor
public class ContextLoaderListener implements ServletContextListener {
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

    private WebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
    }

    private void initWebApplicationContext(ServletContext servletContext) {
        // 从 web.xml 中获取配置文件的路径，默认为 applicationContext.xml
        String configLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        // 通过上一步的配置文件，创建一个 AnnotationConfigApplicationContext 对象
        WebApplicationContext wac = new AnnotationConfigWebApplicationContext(configLocation);
        // 让 AnnotationConfigApplicationContext 对象 和 ServletContext 对象 能相互引用
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }
}
