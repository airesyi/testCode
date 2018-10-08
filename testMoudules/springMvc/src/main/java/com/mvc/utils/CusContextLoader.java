package com.mvc.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CusContextLoader implements ApplicationContextAware {
    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public final static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public final static Object getBean(String beanName, Class<?> requiredType) {
        return context.getBean(beanName, requiredType);
    }
}
