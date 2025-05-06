package com.minis.web;

import com.minis.beans.PropertyEditor;
import com.minis.beans.PropertyValues;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author lnd
 * @Description 
 * @Date 2025/5/3 20:37
 */
public class WebDataBinder {
    private Object target;
    private Class<?> clz;
    private String objectName;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String targetName) {
        this.target = target;
        this.clz = target.getClass();
        this.objectName = targetName;
    }

    /**
     * 核心绑定方法，将 request 中的参数值绑定到目标对象的属性上
     * @param request
     */
    public void bind(HttpServletRequest request) {
        PropertyValues mpvs = assignParameters(request);
        addBindValues(mpvs, request);
        doBind(mpvs);
    }

    private void doBind(PropertyValues mpvs) {
        applyPropertyAccessor().setPropertyValues(mpvs);
    }

    private void applyPropertyAccessor() {
        getPropertyAccessor().setPropertyValues(mpvs);
    }

    /**
     * 设置属性值的工具
     * @return
     */
    protected BeanWrapper getPropertyAccessor() {
        return new BeanWrapperImpl(this.target);
    }

    private void addBindValues(PropertyValues mpvs, HttpServletRequest request) {

    }

    /**
     * 将 request 参数解析成 PropertyValues
     * @param request
     * @return
     */
    private PropertyValues assignParameters(HttpServletRequest request) {
        Map<String, Object> map = WebUtils.getParametersStartingWith(request, "");
        return new PropertyValues(map);
    }


    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }
}
