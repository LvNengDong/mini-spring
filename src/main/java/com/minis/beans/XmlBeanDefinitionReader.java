package com.minis.beans;


import com.minis.beans.BeanDefinition;
import com.minis.beans.SimpleBeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;

import java.util.List;

/**
 * @Author lnd
 * @Description 将内存中的XML配置信息转换成BeanDefinition对象
 * @Date 2023/5/1 19:35
 */
public class XmlBeanDefinitionReader {
    SimpleBeanFactory simpleBeanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory simpleBeanFactory) {
        this.simpleBeanFactory = simpleBeanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        // 这里的 resource.hasNext() 底层调用的是 elementIterator.hasNext() 方法
        while (resource.hasNext()) {
            try {
                Element element = (Element) resource.next();
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                // 处理属性
                List<Element> propertyElements = element.elements("property");
                PropertyValues PVS = new PropertyValues();
                for (Element e : propertyElements) {
                    String pType = e.attributeValue("type");
                    String pName = e.attributeValue("name");
                    String pValue = e.attributeValue("value");
                    PVS.addPropertyValue(new PropertyValue(pType, pName, pValue));
                }
                beanDefinition.setPropertyValues(PVS);
                // 处理构造器属性
                List<Element> constructorElements = element.elements("constructor-arg");
                ArgumentValues AVS = new ArgumentValues();
                for (Element e : constructorElements) {
                    String aType = e.attributeValue("type");
                    String aName = e.attributeValue("name");
                    String aValue = e.attributeValue("value");
                    AVS.addGenericArgumentValue(new ArgumentValue(aType, aName, aValue));
                }
                beanDefinition.setConstructorArgumentValues(AVS);
                // 注册BeanDefinition
                simpleBeanFactory.registerBeanDefinition(beanID, beanDefinition);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
