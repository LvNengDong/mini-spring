package com.minis.context;

import com.apple.eawt.ApplicationEvent;
import com.minis.ApplicationEventPublisher;
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
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

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


    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class getType(String name) {
        return null;
    }
    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }
    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
