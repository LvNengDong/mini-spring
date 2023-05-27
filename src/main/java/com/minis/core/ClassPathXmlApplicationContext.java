package com.minis.core;

import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import com.minis.context.ClassPathXmlResource;
import com.minis.context.Resource;
import com.minis.context.XmlBeanDefinitionReader;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 20:11
 */
public class ClassPathXmlApplicationContext implements BeanFactory{

    public BeanFactory beanFactory;
    /*context负责整合容器的启动过程，读取外部配置，解析Bean定义，创建BeanFactory*/
    public ClassPathXmlApplicationContext(String fileName){
        // 解析 XML 文件中的内容
        Resource resource = new ClassPathXmlResource(fileName);
        // 使用SimpleBeanFactory作为BeanFactory实例
        BeanFactory beanFactory = new SimpleBeanFactory();
        // reader绑定beanFactory实例
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 加载解析的内容，构建 BeanDefinition
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    /*context再对外提供一个getBean，底层调用的是BeanFactory对应的方法*/
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 这个beanFactory是SimpleBeanFactory实例
        return this.beanFactory.getBean(beanName);
    }

    /**
     * 除了通过读取配置文件向BeanFactory中注册BeanDefinition外，还可以直接通过调用这个方法直接向BeanFactory中注册BeanDefinition
     */
    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) throws BeansException {
        this.beanFactory.registerBeanDefinition(beanDefinition);
    }
}
