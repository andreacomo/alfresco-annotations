package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.WebScript;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;


/**
 * <tt>BeanFactoryPostProcessor</tt> for {@link WebScriptConfigurer} annotation.
 *
 * Creates webscript bean name according to Alfresco requirements (<tt>webscript.[name].[http-method]</tt>)
 * from {@link WebScript} instance
 *
 *
 * @author Andrea Como
 */
public class WebScriptConfigurer implements BeanFactoryPostProcessor, PriorityOrdered {

    private static final Logger LOGGER = Logger.getLogger(WebScriptConfigurer.class);

    private int order = Ordered.LOWEST_PRECEDENCE;// default: same as non-Ordered

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
            String[] beanDefinitionNames = factory.getBeanDefinitionNames();
            for (String definitionName : beanDefinitionNames) {
                try {
                    final BeanDefinition bd = beanFactory.getBeanDefinition(definitionName);
                    final String beanClassName = bd.getBeanClassName();
                    if (StringUtils.hasText(beanClassName)) {
                        try {
                            final WebScript webScript = AnnotationUtils.findAnnotation(Class.forName(beanClassName), WebScript.class);
                            if (webScript != null) {
                                final String webScriptName = "webscript." + webScript.value() + "." + webScript.method().toString().toLowerCase();
                                factory.removeBeanDefinition(definitionName);
                                factory.registerBeanDefinition(webScriptName, bd);
                            }
                        } catch (ClassNotFoundException e) {
                            LOGGER.error(e.getMessage(), e);
                            throw new FatalBeanException("Unknown class defined.", e);
                        }
                    }
                } catch (NoSuchBeanDefinitionException ex) {
                    LOGGER.warn(ex.getMessage());
                    continue;
                }
            }
        } else {
            LOGGER.error("Unable to access to DefaultListableBeanFactory, @WebScript annotation won't work properly");
        }
    }
}