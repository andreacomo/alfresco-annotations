package it.cosenonjaviste.alfresco.annotations.processors.runtime;
/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import it.cosenonjaviste.alfresco.annotations.ChildOf;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;


/**
 * <tt>BeanFactoryPostProcessor</tt> for {@link it.cosenonjaviste.alfresco.annotations.ChildOf} annotation.
 * <br>
 * Mostly taken from https://jira.spring.io/browse/SPR-6343 and https://github.com/janesser/spring-framework/blob/SPR-6343/spring-context/src/main/java/org/springframework/context/annotation/ChildOfConfigurer.java
 *
 * @author Jan Esser
 */
public class ChildOfConfigurer implements BeanFactoryPostProcessor, PriorityOrdered {

    private static final Logger LOGGER = Logger.getLogger(ChildOfConfigurer.class);

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
                        final ChildOf childOf = AnnotationUtils.findAnnotation(Class.forName(beanClassName), ChildOf.class);
                        if (childOf != null) {
                            final String parentName = childOf.value();
                            if (StringUtils.hasText(parentName)) {
                                bd.setParentName(parentName);
                            } else
                                throw new FatalBeanException(String.format("%s is @ChildOf annotated, but no value set."));
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
    }
}