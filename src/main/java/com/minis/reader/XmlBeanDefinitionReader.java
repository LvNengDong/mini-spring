package com.minis.reader;

import com.minis.ArgumentValue;
import com.minis.ArgumentValues;
import com.minis.PropertyValue;
import com.minis.PropertyValues;
import com.minis.factory.BeanFactory;
import com.minis.beans.BeanDefinition;
import com.minis.factory.SimpleBeanFactory;
import com.minis.resource.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

import java.beans.ConstructorProperties;
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
    SimpleBeanFactory beanFactory;
    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        log.info("初始化BeanDefinition，start============");
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String beanID = element.attributeValue("id");
            String beanClassName = element.attributeValue("class");
            /** Part3、创建BeanDefinition */
            BeanDefinition beanDefinition = new BeanDefinition(beanID, beanClassName);
            // 处理属性s
            log.info("解析配置文件，处理属性标签<property>");
            List<Element> propertyElements = element.elements("property");
            PropertyValues PVS = new PropertyValues();
            List<String> refs = new ArrayList<>(); // 引用的对象
            for (Element e : propertyElements) {
                String pType = e.attributeValue("type");
                String pName = e.attributeValue("name");
                String pValue = e.attributeValue("value");
                String pRef = e.attributeValue("ref");
                boolean isRef = false;
                String pV = "";
                if (StringUtils.isNotEmpty(pValue)) {
                    /* 在 bean.xml 的 bean 标签中，value 和 ref 是互斥的，value 表示基本数值类型的值， ref 表示引用了其它的 bean 对象*/
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
            log.info("解析配置文件，处理构造器参数标签<constructor-arg>");
            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues AVS = new ArgumentValues();
            for (Element e : constructorElements) {
                String aType = e.attributeValue("type");
                String aName = e.attributeValue("name");
                String aValue = e.attributeValue("value");
                ArgumentValue AV = new ArgumentValue(aType, aName, aValue);
                AVS.addArgumentValue(AV);
            }
            beanDefinition.setConstructorArgumentValues(AVS);
            // 初始化BeanDefinition
            this.beanFactory.registerBeanDefinition(beanDefinition);
            this.beanFactory.registerBeanDefinition(beanID, beanDefinition);
        }
        log.info("初始化BeanDefinition，end============");
    }
}
