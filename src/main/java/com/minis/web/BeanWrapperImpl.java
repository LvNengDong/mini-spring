package com.minis.web;

import com.minis.beans.PropertyEditor;
import com.minis.beans.PropertyEditorRegistrySupport;
import com.minis.beans.PropertyValue;
import com.minis.beans.PropertyValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/4 18:00
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    Object wrappedObject;   // 目标对象
    Class<?> clz;
    PropertyValues pvs;    // 参数值

    public BeanWrapperImpl(Object object) {
        registerDefaultEditors(); //不同数据类型的参数转换器editor
        this.wrappedObject = object;
        this.clz = object.getClass();
    }

    public void setBeanInstance(Object object) {
        this.wrappedObject = object;
    }

    public Object getBeanInstance() {
        return wrappedObject;
    }

    // 绑定参数值
    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    private void setPropertyValue(PropertyValue pv) {

        // 拿到参数处理器
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        PropertyEditor pe = this.getCustomEditor(propertyHandler.getPropertyClz());
        // 找到对应参数类型的 editor
        if (pe == null) {
            pe = this.getDefaultEditor(propertyHandler.getPropertyClz());
        }
        // 设置参数值
        pe.setAsText((String) pv.getValue());
        propertyHandler.setValue(pe.getValue());
    }

    class BeanPropertyHandler {
        Method writeMethod = null;
        Method readMethod = null;
        Class<?> propertyClz = null;

        public BeanPropertyHandler(String propertyName) {
            try {
                Field field = clz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                //获取设置属性的方法，按照约定为setXxxx（）
                this.writeMethod = clz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                //获取读属性的方法，按照约定为getXxxx（）
                this.readMethod = clz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public Object getValue() {
            Object result = null;
            writeMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        //调用setter设置属性值
        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
