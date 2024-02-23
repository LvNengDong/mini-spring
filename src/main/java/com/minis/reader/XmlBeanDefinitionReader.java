package com.minis.reader;

import com.minis.factory.BeanFactory;
import com.minis.beans.BeanDefinition;
import com.minis.resource.Resource;
import org.dom4j.Element;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:18
 */
public class XmlBeanDefinitionReader {

    /**
     *  Q：这个类中为什么需要 beanFactory ？
     *  A：因为我们定义了 BeanFactory 的一个作用是注册 BeanDefinition，而注册 BeanDefinition 首先要解析 Resource 资源文件，
     *  而 XxxReader 的作用就是解析 document 中的 bean 的定义，并根据 bean 的定义
     *  注册 BeanDefinition，所以这里需要注入 beanFactory。
     *  */
    BeanFactory beanFactory;
    public XmlBeanDefinitionReader(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            /** Part3、创建BeanDefinition */
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
