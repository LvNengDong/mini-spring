package com.minis.beans.factory.xml;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.google.common.collect.Lists;
import com.minis.AbstractBeanFactory;
import com.minis.PropertyValue;
import com.minis.PropertyValues;
import com.minis.beans.BeansException;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.factory.support.SimpleBeanFactory;
import com.minis.resource.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:18
 */
@Slf4j
public class XmlBeanDefinitionReader {

    /**
     *  Q：这个类中为什么需要 beanFactory ？
     *  A：因为 XxxReader 的作用就是解析 document 中的 bean 的定义，并根据 bean 的定义
     *  注册 BeanDefinition，而注册 BeanDefinition的方法被抽象在 beanFactory 中了，
     *  所以这里需要注入 beanFactory。
     *  */
    AbstractBeanFactory beanFactory;

    public XmlBeanDefinitionReader(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        log.info("解析Bean配置文件 start ======");
        List<String> unLazyInitBeanNames = Lists.newArrayList();
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            log.info("解析Bean配置文件 创建BeanDefinition对象");
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            log.info("解析Bean配置文件 处理<property>标签");
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>(); // 依赖的对象
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                boolean isRef = false;
                String pV = "";
                if (StringUtils.isNotEmpty(pValue)) {
                    /* 在 bean.xml 的 property 标签中，value 和 ref 是互斥的，value 表示基本数值类型的值， ref 表示引用了其它的 bean 对象*/
                    isRef = false;
                    pV = pValue;
                } else if (StringUtils.isNotEmpty(pRef)) {
                    isRef = true;
                    pV = pRef;
                    refs.add(pRef);
                }
                PropertyValue PV = new PropertyValue(pType, pName, pV, isRef);
                PVS.addPropertyValue(PV);
            }
            beanDefinition.setPropertyValues(PVS);
            beanDefinition.setDependsOn(refs);
            // 处理构造器参数s
            log.info("解析Bean配置文件 处理<constructor-arg>标签");
            List<Element> constructorElements = element.elements("constructor-arg");
            ConstructorArgumentValues AVS = new ConstructorArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                ConstructorArgumentValue AV = new ConstructorArgumentValue(aType, aName, aValue);
                AVS.addArgumentValue(AV);
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            log.info("解析Bean配置文件 end === beanDefinition:{}", JSON.toJSONString(beanDefinition, JSONWriter.Feature.PrettyFormat));
            // 注册BeanDefinition
            /*
                ！！！
                这里需要一次性把 BeanDefinition 加载完再去注册，因为在注册的时候，如果遇到非懒加载情况，会直接执行创建Bean的流程，
                如果此时存在Bean之间的互相依赖，而被依赖的bean对应的BeanDefinition尚未加载到的情况，就会报错。
                所以需要一次性先把所有的 BeanDefinition 都注册完毕
            * */
            if (!beanDefinition.isLazyInit()) {
                unLazyInitBeanNames.add(beanID);
            }
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
        // 如果为非懒加载，在注册完BeanDefinition后就立即创建Bean
        if (CollectionUtils.isNotEmpty(unLazyInitBeanNames)) {
            unLazyInitBeanNames.forEach(beanName -> {
                try {
                    this.beanFactory.getBean(beanName);
                } catch (BeansException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
