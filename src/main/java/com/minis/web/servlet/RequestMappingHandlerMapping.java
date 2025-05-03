package com.minis.web.servlet;

import com.minis.web.RequestMapping;
import com.minis.web.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:13
 */
public class RequestMappingHandlerMapping implements HandlerMapping {

    WebApplicationContext wac;

    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext wac) {
        this.wac = wac;
        initMapping();
    }

    /**
     * 建立URL与调用方法和实例的映射关系，存储在 mappingRegistry 中
     */
    private void initMapping() {
        try {
            String[] controllerNames = this.wac.getBeanDefinitionNames();
            // 扫描 wac 中存放的所有 bean
            for (String controllerName : controllerNames) {
                Class<?> clz = Class.forName(controllerName);
                Object obj = this.wac.getBean(controllerName);
                Method[] methods = clz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        // 建立方法名和URL的映射关系
                        String urlMapping = method.getAnnotation(RequestMapping.class).value();
                        this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                        this.mappingRegistry.getMappingObj().put(urlMapping, obj);
                        this.mappingRegistry.getMappingMethod().put(urlMapping, method);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据访问 URL 查找对应的调用方法
     */
    @Override
    public HandlerMethod getHandler(HttpServletRequest request) throws Exception {
        String sPath = request.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(sPath)) {
            return null;
        }
        Method method = this.mappingRegistry.getMappingMethod().get(sPath);
        Object obj = this.mappingRegistry.getMappingObj().get(sPath);
        return new HandlerMethod(method, obj);
    }
}
