package com.minis.context;

import com.apple.eawt.ApplicationEvent;
import com.minis.ApplicationEventPublisher;
import com.minis.factory.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.factory.SimpleBeanFactory;
import com.minis.reader.XmlBeanDefinitionReader;
import com.minis.resource.ClassPathXmlResource;
import com.minis.resource.Resource;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 22:45
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    private SimpleBeanFactory beanFactory;

    /*
     *  起一个整合作用，串联整个流程
     *   context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
     * */
    public ClassPathXmlApplicationContext(String fileName) {
        // 1、加载配置文件
        Resource resource = new ClassPathXmlResource(fileName);
        beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory); // reader组装一下beanFactory
        // 2、解析配置文件 + 注册 BeanDefinition
        reader.loadBeanDefinitions(resource);
    }


    /**
     * context再对外提供一个 getBean，底层就是调用的BeanFactory 的 getBean 方法
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    /**
     * 装饰器模式
     *
     * context 再对外提供一个 registerBeanDefinition，这保证了即使没有配置文件，我们也可以通过手动调用
     *  ClassPathXmlApplicationContext#registerBeanDefinition(com.minis.beans.BeanDefinition) 方法来实现 Bean 的注册功能
     *
     * 底层就是调用的 SimpleBeanFactory 的 registerBeanDefinition 方法，
     * 用于手动注册 BeanDefinition 到容器中
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanDefinition);

    @Override
    public boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class getType(String name) {
        return this.beanFactory.getType(name);
    }
    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }
    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
