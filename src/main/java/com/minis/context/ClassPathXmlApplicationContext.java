package com.minis.context;

import com.minis.*;
import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.support.SimpleBeanFactory;
import com.minis.beans.factory.xml.XmlBeanDefinitionReader;
import com.minis.resource.ClassPathXmlResource;
import com.minis.resource.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 22:45
 */
@Slf4j
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 起一个整合作用，串联整个流程
     *  context负责整合容器的启动过程，读外部配置，解析Bean定义，创建BeanFactory
     *  */
    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        // 1、加载配置文件
        Resource resource = new ClassPathXmlResource(fileName);
        beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        // 注册BeanPostProcessor，防止有些非懒加载的bean不能被BeanPostProcessor处理
        log.info("将BeanPostProcessor注册到BeanFactory中");
        registerBeanPostProcessors(this.beanFactory);
        // 2、解析配置文件 + 注册 BeanDefinition
        reader.loadBeanDefinitions(resource);
        if (isRefresh) {
            log.info("ClassPathXmlApplicationContext >> refresh");
            //beanFactory.refresh();
            refresh();
        }
    }

    private void refresh() {
        log.info("将BeanPostProcessor注册到BeanFactory中");
        registerBeanPostProcessors(this.beanFactory);
        log.info("刷新容器中的所有Bean");
        onRefresh();
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

    private void registerBeanPostProcessors(AbstractAutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
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

    @Override
    public void addApplicationListener(ApplicationListener listener) {

    }
}
