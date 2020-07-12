package com.monkeyk.os.domain.shared;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author Shengzhao Li
 */
public abstract class BeanProvider {

    private static ApplicationContext applicationContext;

    /**
     * @since 2.0.0
     */
    private static BeanFactory beanFactory;

    private BeanProvider() {
    }

    public static void initialize(ApplicationContext applicationContext) {
        BeanProvider.applicationContext = applicationContext;
        BeanProvider.beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * Get Bean by clazz.
     *
     * @param clazz Class
     * @param <T>   class type
     * @return Bean instance
     */
    public static <T> T getBean(Class<T> clazz) {
        if (beanFactory == null) {
            return null;
        }
        return beanFactory.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (beanFactory == null) {
            return null;
        }
        return (T) beanFactory.getBean(beanId);
    }


}