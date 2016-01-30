package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.StringUtils;

/**
 * <tt>BeanFactoryPostProcessor</tt> for annotation processing during Spring context startup.
 *
 * <br>
 * Mostly taken from https://jira.spring.io/browse/SPR-6343 and https://github.com/janesser/spring-framework/blob/SPR-6343/spring-context/src/main/java/org/springframework/context/annotation/ChildOfConfigurer.java
 *
 * @author Andrea Como
 */
public abstract class AbstractPostProcessorConfigurer implements BeanFactoryPostProcessor, PriorityOrdered {

    protected final Logger logger = Logger.getLogger(this.getClass());

    private int order = Ordered.LOWEST_PRECEDENCE;// default: same as non-Ordered

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String definitionName : beanDefinitionNames) {
            try {
                final BeanDefinition bd = beanFactory.getBeanDefinition(definitionName);
                final String beanClassName = bd.getBeanClassName();
                if (StringUtils.hasText(beanClassName)) {
                    try {
                        processBeanDefinition(beanFactory, bd, beanClassName, definitionName);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new FatalBeanException("Unknown class defined.", e);
                    }
                }
            } catch (NoSuchBeanDefinitionException ex) {
                logger.warn(ex.getMessage());
                continue;
            }
        }
    }

    protected abstract void processBeanDefinition(ConfigurableListableBeanFactory beanFactory, BeanDefinition bd, String beanClassName, String definitionName) throws FatalBeanException;

}
