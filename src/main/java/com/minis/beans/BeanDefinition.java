package com.minis.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author lnd
 * @Description
 * @Date 2023/4/22 22:43
 */
@Data
public class BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    /** 表示bean要不要再加载的时候初始化 */
    private boolean lazyInit =false;
    /** 记录bean之间的依赖关系 */
    private String[] dependsOn;
    private ArgumentValues constructorArgumentValues;
    private PropertyValues propertyValues;
    /** 初始化方法的名称 */
    private String initMethodName;
    /** bean对应的class类型？？*/
    private volatile Object beanClass;
    private String id;
    private String className;
    /** 表示bean是单例还是原型 */
    private String scope = SCOPE_SINGLETON;


    public BeanDefinition(String id, String className) {
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
