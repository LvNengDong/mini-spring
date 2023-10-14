package com.minis.context;

import com.minis.factory.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.factory.SimpleBeanFactory;
import com.minis.beans.BeanDefinition;
import com.minis.reader.XmlBeanDefinitionReader;
import com.minis.resource.ClassPathXmlResource;
import com.minis.resource.Resource;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 22:45
 */
public class ClassPathXmlApplicationContext {

    private BeanFactory beanFactory;

    /*
     *  起一个整合作用，串联整个流程
     *   context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
     * */
    public ClassPathXmlApplicationContext(String fileName) {
        // 1、加载配置文件
        Resource resource = new ClassPathXmlResource(fileName);
        beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 2、解析配置文件 + 注册 BeanDefinition
        reader.loadBeanDefinitions(resource);
    }


    /**
     * context再对外提供一个getBean，底层就是调用的BeanFactory对应的方法
     */
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    /**
     * context再对外提供一个registerBeanDefinition，底层就是调用的BeanFactory对应的方法
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
