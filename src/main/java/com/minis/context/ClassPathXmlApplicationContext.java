package com.minis.context;

import com.minis.beans.BeanFactory;
import com.minis.beans.BeansException;
import com.minis.beans.SimpleBeanFactory;
import com.minis.beans.XmlBeanDefinitionReader;
import com.minis.core.*;

/**
 * @Author lnd
 * @Description
 * @Date 2023/5/1 20:11
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    public SimpleBeanFactory beanFactory;
    /*context负责整合容器的启动过程，读取外部配置，解析Bean定义，创建BeanFactory*/
    public ClassPathXmlApplicationContext(String fileName){
        // 解析 XML 文件中的内容
        Resource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory beanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 加载解析的内容，构建 BeanDefinition
        reader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
    }

    /*context再对外提供一个getBean，底层调用的是BeanFactory对应的方法*/
    @Override
    public Object getBean(String beanName) throws BeansException {
        //读取 BeanDefinition 的配置信息，实例化 Bean，然后把它注入到 BeanFactory 容器中
        return this.beanFactory.getBean(beanName);
    }


    @Override
    public Boolean containsBean(String name) {
        return this.beanFactory.containsBean(name);
    }

    @Override
    public void registerBean(String beanName, Object obj) {
        this.beanFactory.registerBean(beanName, obj);
    }

    @Override
    public boolean isSingleton(String name) {
        // TODO
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        // TODO
        return false;
    }

    @Override
    public Class getType(String name) {
        // TODO
        return null;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }
}
