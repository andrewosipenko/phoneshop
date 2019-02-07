package com.es.core.utils;


import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class LoggingAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        ReflectionUtils.doWithFields(bean.getClass(), (field) -> {
            InjectLogger annotation = field.getAnnotation(InjectLogger.class);
            if(annotation != null) {
                boolean accessibility = field.isAccessible();
                field.setAccessible(true);
                if(!annotation.value().isEmpty()){
                    field.set(bean, LogFactory.getLog(annotation.value()));
                } else {
                    field.set(bean, LogFactory.getLog(bean.getClass()));
                }
                field.setAccessible(accessibility);
            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}