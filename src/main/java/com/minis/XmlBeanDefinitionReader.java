package com.minis;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:18
 */
public class XmlBeanDefinitionReader {
    //BeanFactory beanFactory;
    //public XmlBeanDefinitionReader(BeanFactory beanFactory) {
    //    this.beanFactory = beanFactory;
    //}
    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            this.beanFactory.registerBeanDefinition(beanDefinition);
        }
    }
}
