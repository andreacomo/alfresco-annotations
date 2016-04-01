package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.ActivitiBean;
import it.cosenonjaviste.alfresco.annotations.OnCompleteTaskListener;
import it.cosenonjaviste.alfresco.annotations.OnCreateTaskListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.logging.Logger;

/**
 * post processor to register into activityRegistry a bean annotated with ActivitiBean
 * <p>
 * Created by albertorugnone on 21/03/16.
 */
@Component
public class ActivitiBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    public static final String ACTIVITI_BEAN_REGISTRY = "activitiBeanRegistry";

    private static Logger LOGGER = Logger.getLogger(ActivitiBeanPostProcessor.class.getName());

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String defaultName) throws BeansException {

        if (!applicationContext.containsBean(ACTIVITI_BEAN_REGISTRY)) {
            LOGGER.finer("appliction context doesn't contain activitiBeanRegistry ");
            return bean;
        }

        final Map<String, Object> registry = (Map<String, Object>) applicationContext.getBean(ACTIVITI_BEAN_REGISTRY);
        add(bean, registry, ActivitiBean.class, defaultName);
        add(bean, registry, OnCreateTaskListener.class, defaultName);
        add(bean, registry, OnCompleteTaskListener.class, defaultName);

        return bean;
    }

    private void add(Object beanToRegister, Map<String, Object> registry, Class<? extends Annotation> annotationType, String defaultValue) {
        if (AnnotationUtils.isAnnotationDeclaredLocally(annotationType, beanToRegister.getClass())) {
            final Annotation activitiBeanAnnotation = AnnotationUtils.findAnnotation(beanToRegister.getClass(), annotationType);
            final String value = (String) AnnotationUtils.getValue(activitiBeanAnnotation);
            if (StringUtils.isEmpty(value)) {
                registry.put(defaultValue, beanToRegister);
            } else {
                registry.put(value, beanToRegister);
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
