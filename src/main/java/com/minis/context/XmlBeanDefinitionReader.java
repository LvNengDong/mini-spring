package com.minis.context;


import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.context.Resource;
import com.minis.core.BeanFactory;
import org.dom4j.Element;

/**
 * @Author lnd
 * @Description 将内存中的XML配置信息转换成BeanDefinition对象
 * @Date 2023/5/1 19:35
 */
public class XmlBeanDefinitionReader {
    BeanFactory beanFactory;

    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            try {
                Element element = (Element) resource.next();
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                beanFactory.registerBeanDefinition(beanDefinition);
            } catch (BeansException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
