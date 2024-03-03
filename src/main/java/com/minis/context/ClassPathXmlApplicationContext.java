package com.minis.context;

import com.google.common.collect.Lists;
import com.minis.*;
import com.minis.beans.BeansException;
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
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    DefaultListableBeanFactory beanFactory;

    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = Lists.newArrayList();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

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
        //log.info("将BeanPostProcessor注册到BeanFactory中");
        //registerBeanPostProcessors(this.beanFactory);
        // 2、解析配置文件 + 注册 BeanDefinition
        reader.loadBeanDefinitions(resource);
        if (isRefresh) {
            log.info("ClassPathXmlApplicationContext >> refresh");
            try {
                refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        log.info("postProcessBeanFactory....");
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    protected void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }
    @Override
    protected void initApplicationEventPublisher() {
        SimpleApplicationEventPublisher aep = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(aep);
    }

    @Override
    protected void registerListeners() {
        ApplicationListener listener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }
    @Override
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent("Context Refreshed..."));
    }
    /**
     * context再对外提供一个getBean，底层就是调用的BeanFactory对应的方法
     */
    public Object getBean(String beanName) throws BeansException {
        return beanFactory.getBean(beanName);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.getApplicationEventPublisher().publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.getApplicationEventPublisher().addApplicationListener(listener);
    }
}
