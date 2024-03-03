package com.minis;

import com.google.common.collect.Lists;
import com.minis.beans.BeansException;

import java.util.List;
import java.util.Objects;

/**
 * @Author lnd
 * @Description
 *      extends AbstractBeanFactory 的作用是为了得到 BeanFactory 的基础能力；
 *      implements AutowireCapableBeanFactory 的作用是为了获取解析 Autowire 的能力
 * @Date 2024/3/1 22:58
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
    implements AutowireCapableBeanFactory {

    private final List<BeanPostProcessor> beanPostProcessors = Lists.newArrayList();

    /*
    * AbstractAutowireCapableBeanFactory
    *   extends AbstractBeanFactory
    *    extends ConfigurableBeanFactory
    * */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /*
     * AbstractAutowireCapableBeanFactory
     *   extends AbstractBeanFactory
     *    extends ConfigurableBeanFactory
     * */
    @Override
    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }


    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            beanProcessor.setBeanFactory(this);
            result = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (Objects.isNull(result)) {
                return result;
            }
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (Objects.isNull(result)) {
                return result;
            }
        }
        return result;
    }
}
