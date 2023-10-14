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

    /** Part3：Bean的内存映像 */
    private List<BeanDefinition> beanDefinitions = Lists.newArrayList();
    /** Part5：保存Bean (Map)*/
    private Map<String, Object> singletons = Maps.newHashMap();

    /**
     * 在构造器方法中做两件事
     * 1、读取外存中的配置文件，
     * 2、解析出bean的定义，形成对应的内存映像
     */
    public ClassPathXmlApplicationContext(String fileName) {
        this.readXml(fileName);
        this.instanceBeans();
    }

    /**
     * 利用反射创建bean实例，并存储在singletons中
     */
    private void instanceBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            String id = beanDefinition.getId();
            String className = beanDefinition.getClassName();
            try {
                /** Part4:创建实例Bean */
                Object instance = Class.forName(className).newInstance();
                singletons.put(id, instance);
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Part6：获取Bean
     * 这是对外的一个方法，让外部程序从容器中获取Bean实例，会逐步演化成核心方法
     */
    public Object getBean(String beanName) {
        return singletons.get(beanName);
    }

    /**
     *
     * */
    private void readXml(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            //对配置文件中的每一个<bean>，进行处理
            for (Element element : (List<Element>) rootElement.elements()) {
                //获取Bean的基本信息
                String beanID = element.attributeValue("id");
                String beanClassName = element.attributeValue("class");
                BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
                //将Bean的定义存放到beanDefinitions
                beanDefinitions.add(beanDefinition);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
