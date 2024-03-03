package com.minis.beans.factory.config;

import com.minis.beans.PropertyValues;
import lombok.Data;

import java.util.List;

/**
 * @Author lnd
 * @Description BeanDefinition直接映射XML文件中bean的定义
 * @Date 2023/4/22 22:43
 */
@Data
public class BeanDefinition {
    private String id;
    private String className;
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    private boolean lazyInit = false;
    /* 记录 Bean 之间的依赖关系 */
    private List<String> dependsOn;

    /* 构造器参数 */
    private ConstructorArgumentValues constructorArgumentValues;
    /* property 列表 */
    private PropertyValues propertyValues;
    /* 当一个 Bean 构造好并实例化之后是否要让框架调用初始化方法 */
    private String initMethodName;
    private volatile Object beanClass;
    private String scope = SCOPE_SINGLETON;

    public BeanDefinition (String id, String className) {
        this.id = id;
        this.className = className;
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }
}
