package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.JsExtension;
import org.alfresco.repo.processor.BaseProcessorExtension;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


/**
 * <tt>BeanPostProcessor</tt> for {@link it.cosenonjaviste.alfresco.annotations.JsExtension} annotation.
 *
 * <p>
 *     This processor register {@link JsExtension#value()} as <strong>extension name</strong>
 * </p>
 *
 * @author Andrea Como
 */
public class JsExtensionConfigurer implements BeanPostProcessor {

    private static final Logger LOGGER = Logger.getLogger(JsExtensionConfigurer.class);

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BaseProcessorExtension && bean.getClass().getAnnotation(JsExtension.class) != null) {
            BaseProcessorExtension extension = (BaseProcessorExtension) bean;
            extension.setExtensionName(extension.getClass().getAnnotation(JsExtension.class).value());
            LOGGER.debug("Extension name set to bean " + beanName);
        }
        return bean;
    }
}