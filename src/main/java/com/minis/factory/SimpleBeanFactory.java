package com.minis.factory;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minis.*;
import com.minis.beans.BeanDefinition;
import com.minis.beans.BeansException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description Q：SimpleBeanFactory 继承 DefaultSingletonBeanRegistry 的原因是什么？
 * A：确保我们通过 SimpleBeanFactory 创建的 Bean 默认就是单例的。
 * 我们需要将 SimpleBeanFactory 中关于创建 Bean 的直接方法，而是间接使用 DefaultSingletonBeanRegistry 中创建 Bean 的方法，
 * 以确保得到单例 bean。
 * DefaultSingletonBeanRegistry 通过 SimpleBeanFactory 对外暴露服务，而在 Spring 中默认创建的bean就是单例的，
 * 正是通过 `SimpleBeanFactory extends DefaultSingletonBeanRegistry` 来实现的
 * @Date 2023/10/14 14:12
 */
@Slf4j
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    // BeanDefinition不涉及单例多例的概念，所以放在比较通用的 SimpleBeanFactory 类中即可
    private Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();
    private List<String> beanDefinitionNames = Lists.newArrayList();

    /**
     * Bean容器
     *  因为 DefaultSingletonBeanRegistry 维护了一个单例 singletons，所以这里直接使用其父类中的，确保默认情况下创建的是单例 bean
     *  同理，beanNames 也是类似的
     */
    // private Map<String, Object> singletons = new HashMap<>();
    // private List beanNames = new ArrayList<>();


    /**
     * 创建所有的bean
     *
     * Spring默认是懒加载的，即只有调用 getBean 时才会开始创建相关的Bean出来，
     * 而 refresh 就是这样一个方法，调用容器中所有对象的 getBean 方法，一次性将
     * 所有的 Bean 创建出来
     */
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (Exception e) {
                log.error("Refresh bean fail, beanName:{}", beanName);
            }
        }
    }

    /**
     * parentClass ： BeanFactory
     * Part6、保存 bean 到 bean 容器中
     * <p>
     * 1、保存 bean 到 map（singletons） 中
     * 2、获取 bean
     */
    @Override
    public Object getBean(String beanName) throws BeansException {
        // 先尝试直接获取Bean实例，如果此时还没有这个Bean的实例，则获取它的定义来创建实例
        log.info("getBean >> 尝试直接获取Bean实例 beanName:{}", beanName);
        Object singleton = super.getSingleton(beanName);
        if (singleton == null) {
            // 如果没有实例bean，则尝试从毛坯实例中获取
            log.info("getBean >> 尝试从三级缓存中获取早期bean实例 beanName:{}", beanName);
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) { // 如果连毛胚都没有，则创建bean实例并注册
                log.info("getBean >> 开始创建bean，getBeanDefinition beanName:{}", beanName);
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                /* Part4、创建实例Bean */
                singleton = createBean(beanName, beanDefinition);
                log.info("getBean >> 注册实例Bean到容器中 beanName: {}", beanDefinition.getId());
                this.registerSingleton(beanDefinition.getId(), singleton);
                // 预留 beanpostprocessor 位置
                // step1：postProcessBeforeInitialization
                // step2：afterPropertiesSet
                // step3：init-method
                // step4：postProcessAfterInitialization
            }
            log.info("getBean end success beanName:{} bean:{}", beanName, JSON.toJSONString(singleton));
            return singleton;
        }
        return null;
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        try {
            if (beanDefinition == null) {
                throw new BeansException(beanName + " 对应的beanDefinition不存在");
            }
            // 创建毛坯bean实例
            Object obj = doCreateBean(beanDefinition);
            log.info("getBean >> createBean 保存三级缓存Bean到earlySingletonObjects中 beanName: {}", beanDefinition.getId());
            this.earlySingletonObjects.put(beanDefinition.getId(), obj);
            /**
             * !!!!!!!
             *  根据属性注入必须放在构造函数之后，因为根据属性注入必须使用对象的 setter 方法，通过反射调用这个方法时必须保证对应的实力bean已经被创建成功了
             */
            Class<?> clz = Class.forName(beanDefinition.getClassName());
            // 补齐毛坯Bean中property的值
            handleProperties(beanDefinition, clz, obj);
            return obj;
        } catch (Exception e) {
            log.error("根据beanDefinition创建Bean实例异常,beanDefinition:{}", JSON.toJSONString(beanDefinition), e);
            return null;
        }
    }

    /**
     * doCreateBean 创建毛坯 bean 实例，仅仅调用构造方法，没有进行属性处理
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        log.info("getBean >> createBean >> doCreateBean 创建三级缓存Bean(根据构造函数创建实例bean) beanName: {}", beanDefinition.getId());
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            // handle constructor
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (argumentValues.isNotEmpty()) {
                int argumentCount = argumentValues.getArgumentCount();
                Class<?>[] paramTypes = new Class<?>[argumentCount];
                Object[] paramValues = new Object[argumentCount];
                // 对每一个参数，分数据类型分别处理
                for (int i = 0; i < argumentCount; i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        Object value = argumentValue.getValue();
                        paramValues[i] = value;
                    } else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else if ("int".equals(argumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) argumentValue.getValue());
                    } else { // 默认为String
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                }
                // 按照特定构造器创建实例
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValues);
            } else { // 如果没有参数，直接根据无参构造方法创建实例
                obj = clz.newInstance();
            }
            return obj;
        } catch (Exception e) {
            log.info("根据构造函数创建实例bean时发生异常：{}", beanDefinition.getClassName(), e);
            return null;
        } finally {
            log.info("getBean >> createBean >> doCreateBean 创建三级缓存Bean(根据构造函数创建实例bean) beanName: {}  =====end", beanDefinition.getId());
        }
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        log.info("getBean >> createBean >> handleProperties beanName:{} start====", beanDefinition.getId());
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (propertyValues.isNotEmpty()) {
                for (int i = 0; i < propertyValues.size(); i++) {
                    // 对每一个属性，分数据类型分别处理
                    PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                    String pType = propertyValue.getType();
                    String pName = propertyValue.getName();
                    Object pValue = propertyValue.getValue();
                    boolean isRef = propertyValue.isRef();
                    Class<?> paramType = null;
                    Object paramValue = new Object();
                    if (!isRef) { // 不是ref，只是普通属性
                        if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                            paramType = String.class;
                        } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                            paramType = Integer.class;
                        } else if ("int".equals(pType)) {
                            paramType = int.class;
                        } else { // 默认为string
                            paramType = String.class;
                        }
                        paramValue = pValue;
                    } else { // 如果是ref，需要先创建依赖的bean
                        try {
                            paramType = Class.forName(pType);
                        } catch (Exception e) {
                            log.error("解析XML中的bean类型失败，不存在该类型的Bean,{}", paramType, e);
                        }
                        // 再次调用getBean创建ref的bean实例（递归的创建依赖的bean）
                        paramValue = getBean((String) pValue);
                    }
                    // 按照 setXxx 规范查找 setter 方法，调用 setter 方法设置属性
                    String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
                    Method method = clz.getMethod(methodName, paramType);
                    method.invoke(obj, paramValue);
                }
            }
            log.info("getBean >> createBean >> handleProperties beanName:{} end======", beanDefinition.getId());
        } catch (Exception e) {
            log.error("根据set方法创建实例Bean异常", e);
        }
    }

    /**
     * parentClass ： BeanFactory
     */
    @Override
    public void registerBean(String beanName, Object obj) {
        // 使用 DefaultSingletonBeanRegistry 的实现
        super.registerSingleton(beanName, obj);
    }

    /**
     * parentClass ： BeanFactory
     */
    @Override
    public boolean containsBean(String beanName) {
        // 使用 DefaultSingletonBeanRegistry 的实现
        return super.containsSingleton(beanName);
    }

    /**
     * parentClass ： BeanFactory
     */
    @Override
    public boolean isSingleton(String name) {
        return beanDefinitionMap.get(name).isSingleton();
    }

    /**
     * parentClass ： BeanFactory
     */
    @Override
    public boolean isPrototype(String name) {
        return beanDefinitionMap.get(name).isPrototype();
    }

    /**
     * parentClass ： BeanFactory
     */
    @Override
    public Class getType(String name) {
        return null;
    }

    /**
     * parentClass ： BeanDefinitionRegistry
     * 作用： 注册 BeanDefinition
     * 备注： 目前这个方法不再是继承自 BeanFactory 接口中的方法了，而是继承自 BeanDefinitionRegistry 中的方法
     */
    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        log.info("注册BeanDefinition name:{}", name);
        beanDefinitionMap.put(name, beanDefinition);
        if (!beanDefinition.isLazyInit()) {
            log.info("非懒加载的bean，立即创建bean实例。beanName:{}", name);
            try {
                getBean(name);
            } catch (BeansException e) {
                log.info("创建bean异常 beanName:{}", name);
            }
        }
    }

    /*
     * parentClass ： BeanDefinitionRegistry
     * */
    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    /*
     * parentClass ： BeanDefinitionRegistry
     * */
    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    /*
     * parentClass ： BeanDefinitionRegistry
     * */
    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }
}
