package com.minis;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minis.beans.BeansException;
import com.minis.beans.factory.config.BeanDefinition;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.factory.support.BeanDefinitionRegistry;
import com.minis.beans.factory.support.DefaultSingletonBeanRegistry;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description 抽取 SimpleBeanFactory 和 AutowireCapableBeanFactory 中公共的部分到当前类中
 * @Date 2024/2/26 23:53
 */
@Slf4j
@NoArgsConstructor
public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry
        implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    protected Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();
    protected List<String> beanDefinitionNames = Lists.newArrayList();

    /*
     * parentClass ： BeanDefinitionRegistry
     * 作用： 注册 BeanDefinition
     * 备注： 目前这个方法不再是继承自 BeanFactory 接口中的方法了，而是继承自 BeanDefinitionRegistry 中的方法
     */
    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        log.info("注册BeanDefinition name:{}", name);
        beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
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
        return this.beanDefinitionMap.get(name).getClass();
    }

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
                log.info("执行 refresh 方法创建 bean beanName:{}", beanName);
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
        log.info("AbstractBeanFactory#getBean >> 尝试直接从单例池中获取Bean实例 beanName:{}", beanName);
        Object singleton = super.getSingleton(beanName);
        if (singleton == null) {
            log.info("AbstractBeanFactory#getBean >> 单例池中不存在该Bean实例，尝试从三级缓存(毛坯bean容器)中获取早期bean实例 beanName:{}", beanName);
            singleton = this.earlySingletonObjects.get(beanName);
            if (singleton == null) { // 如果连毛胚都没有，则创建bean实例并注册
                log.info("AbstractBeanFactory#getBean >> 单例池和三级缓存(毛坯bean容器)中都不存在该bean实例，开始根据BeanDefinition创建bean beanName:{}", beanName);
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                singleton = createBean(beanDefinition);
                log.info("AbstractBeanFactory#getBean >> 根据BeanDefinition创建bean完成，保存bean实例到bean单例池中 beanName:{}", beanName);
                this.registerSingleton(beanDefinition.getId(), singleton);
                // 进行 beanPostProcessor 处理
                // step1：postProcessBeforeInitialization
                applyBeanPostProcessorsBeforeInitialization(singleton, beanName);
                // step2：afterPropertiesSet
                // step3：init-method
                if (StringUtils.isNotEmpty(beanDefinition.getInitMethodName())) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // step4：postProcessAfterInitialization
                applyBeanPostProcessorsAfterInitialization(singleton, beanName);
                log.info("AbstractBeanFactory#getBean >> end beanName:{} bean:{}", beanName, JSON.toJSONString(singleton));
                return singleton;
            }
            log.info("AbstractBeanFactory#getBean >> 从三级缓存(毛坯bean容器)中获取早期bean实例成功，直接返回bean实例 beanName:{}", beanName);
            return singleton;
        }
        log.info("getBean >> 尝试直接从单例池中获取Bean实例成功，直接返回单例对象 beanName:{}", beanName);
        return singleton;
    }

    abstract public Object applyBeanPostProcessorsAfterInitialization(Object singleton, String beanName) throws BeansException;

    private void invokeInitMethod(BeanDefinition beanDefinition, Object singleton) {
        try {
            Class<?> clz = beanDefinition.getClass();
            String initMethodName = beanDefinition.getInitMethodName();
            Method method = clz.getMethod(initMethodName);
            method.invoke(singleton);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    abstract public Object applyBeanPostProcessorsBeforeInitialization(Object singleton, String beanName) throws BeansException;

    private Object createBean(BeanDefinition beanDefinition) {
        try {
            if (beanDefinition == null) {
                throw new BeansException("beanDefinition不存在");
            }
            // 创建毛坯bean实例
            log.info("AbstractBeanFactory#getBean>>createBean 创建三级缓存Bean(毛坯Bean) beanName: {}", beanDefinition.getId());
            Object obj = doCreateBean(beanDefinition);
            log.info("AbstractBeanFactory#getBean>>createBean 保存三级缓存Bean到earlySingletonObjects中 beanName: {}", beanDefinition.getId());
            this.earlySingletonObjects.put(beanDefinition.getId(), obj);
            /**
             * !!!!!!!
             *  根据属性注入必须放在构造函数之后，因为根据属性注入必须使用对象的 setter 方法，通过反射调用这个方法时必须保证对应的实力bean已经被创建成功了
             */
            Class<?> clz = Class.forName(beanDefinition.getClassName());
            // 完善bean，主要是处理属性
            log.info("AbstractBeanFactory#getBean>>createBean 完善三级缓存Bean(毛坯Bean) beanName: {}", beanDefinition.getId());
            populateBean(beanDefinition, clz, obj);
            return obj;
        } catch (Exception e) {
            log.error("根据beanDefinition创建Bean实例异常,beanDefinition:{}", JSON.toJSONString(beanDefinition), e);
            return null;
        }
    }

    private void populateBean(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        // 补齐毛坯Bean中property的值
        log.info("AbstractBeanFactory#getBean>>createBean>>populateBean 完善三级缓存Bean(毛坯Bean)，处理bean中的属性 beanName: {}", beanDefinition.getId());
        handleProperties(beanDefinition, clz, obj);
    }

    /**
     * doCreateBean 创建毛坯 bean 实例，仅仅调用构造方法，没有进行属性处理
     */
    private Object doCreateBean(BeanDefinition beanDefinition) {
        log.info("AbstractBeanFactory#getBean>>createBean>>doCreateBean 创建三级缓存Bean(根据构造函数创建实例bean) beanName: {}", beanDefinition.getId());
        Class<?> clz = null;
        Object obj = null;
        Constructor<?> con = null;
        try {
            // handle constructor
            clz = Class.forName(beanDefinition.getClassName());
            // 处理构造器参数
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            if (constructorArgumentValues.isNotEmpty()) {
                log.info("AbstractBeanFactory#getBean>>createBean>>doCreateBean 创建三级缓存Bean(根据构造函数创建实例bean)时构造函数的参数个数大于0，使用有参构造函数 beanName: {}", beanDefinition.getId());
                int argumentCount = constructorArgumentValues.getArgumentCount();
                Class<?>[] paramTypes = new Class<?>[argumentCount];
                Object[] paramValues = new Object[argumentCount];
                // 对每一个参数，分数据类型分别处理
                for (int i = 0; i < argumentCount; i++) {
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        Object value = constructorArgumentValue.getValue();
                        paramValues[i] = value;
                    } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else { // 默认为String
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                // 按照特定构造器创建实例
                con = clz.getConstructor(paramTypes);
                obj = con.newInstance(paramValues);
            } else { // 如果没有参数，直接根据无参构造方法创建实例
                log.info("AbstractBeanFactory#getBean>>createBean>>doCreateBean 创建三级缓存Bean(根据构造函数创建实例bean)时构造函数的参数个数等于0，使用【无参构造函数】 beanName: {}", beanDefinition.getId());
                obj = clz.newInstance();
            }
            return obj;
        } catch (Exception e) {
            log.info("AbstractBeanFactory#getBean>>createBean>>doCreateBean 创建三级缓存Bean出错 beanName:{}", beanDefinition.getClassName(), e);
            return null;
        } finally {
            log.info("AbstractBeanFactory#getBean>>createBean>>doCreateBean 创建三级缓存Bean完成 beanName:{}", beanDefinition.getId());
        }
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clz, Object obj) {
        try {
            log.info("AbstractBeanFactory#getBean>>createBean>>populateBean>>handleProperties 完善三级缓存Bean(毛坯Bean)，处理bean中的属性 beanName: {}", beanDefinition.getId());
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
        } catch (Exception e) {
            log.error("AbstractBeanFactory#getBean>>createBean>>populateBean>>handleProperties 填充bean属性时发生异常", e);
        }
    }
}
