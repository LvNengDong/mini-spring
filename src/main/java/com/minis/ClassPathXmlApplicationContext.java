package com.minis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 22:45
 */
public class ClassPathXmlApplicationContext {

    private final List<BeanDefinition> beanDefinitions = Lists.newArrayList();
    /**
     * Bean容器 (Map)
     */
    private final Map<String, Object> singletons = Maps.newHashMap();

    public ClassPathXmlApplicationContext(String fileName) {
        // 1、加载配置文件到内存中，并解析成 BeanDefinition
        this.readXml(fileName);
        // 2、根据 BeanDefinition 创建Bean实例
        this.instanceBeans();
    }

    /**
     * 根据 BeanDefinition 创建Bean实例
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String id = beanDefinition.getId();
            String className = beanDefinition.getClassName();
            try {
                Object instance = Class.forName(className).newInstance();
                singletons.put(id, instance);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * 获取Bean
     */
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    /**
     * 加载配置文件到内存中，并解析成 BeanDefinition
     */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement(); // rootElement：<beans></beans>
            List<Element> beans = rootElement.elements();
            // 对配置文件中的每一个<bean>，进行处理
            for (Element bean : beans) {
                // 获取Bean的基本信息
                String beanID = bean.attributeValue("id");
                String beanClassName = bean.attributeValue("class");
                // 将Bean的定义存放到beanDefinitions
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
