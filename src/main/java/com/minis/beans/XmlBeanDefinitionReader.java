package com.minis.beans;


import com.minis.beans.BeanDefinition;
import com.minis.beans.SimpleBeanFactory;
import com.minis.core.Resource;
import org.dom4j.Element;

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
        while (resource.hasNext()) {
            try {
                Element element = (Element) resource.next();
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                simpleBeanFactory.registerBeanDefinition(beanDefinition);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
