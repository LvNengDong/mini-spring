package com.minis.beans.factory.annotation;

import com.minis.beans.factory.config.BeanPostProcessor;
import com.minis.beans.BeansException;
import com.minis.beans.factory.BeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @Author lnd
 * @Description
 * @Date 2024/2/26 23:32
 */
@Slf4j
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    public BeanFactory getBeanFactory() { return beanFactory; }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (Objects.nonNull(fields)) {
            // 判断某个对象的属性字段中是否含有 @Autowired 注解
            for (Field field : fields) {
                boolean isAutowired = field.isAnnotationPresent(Autowired.class);
                if (isAutowired) {
                    try {
                        String fieldName = field.getName();
                        // 创建当前bean依赖的bean
                        Object autowiredObj = this.beanFactory.getBean(fieldName);
                        // 将当前bean依赖的bean设置到当前bean的属性中
                        field.setAccessible(true);
                        field.set(bean, autowiredObj);
                        log.info("根据Autowired注解依赖注入完成 {} 依赖于 {}", beanName, fieldName);
                    } catch (Exception e) {

                    }
                }
            }
        }
        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
