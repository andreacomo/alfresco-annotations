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
package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.WebScript;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <tt>BeanFactoryPostProcessor</tt> for {@link WebScriptConfigurer} annotation.
 *
 * Creates webscript bean name according to Alfresco requirements (<tt>webscript.[name].[http-method]</tt>)
 * from {@link WebScript} instance
 *
 * @author Andrea Como
 */
@Component
public class WebScriptConfigurer extends AbstractPostProcessorConfigurer {

    @Override
    protected void processBeanDefinition(ConfigurableListableBeanFactory beanFactory, BeanDefinition bd, String beanClassName, String definitionName) throws FatalBeanException {
        if (beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory factory = (DefaultListableBeanFactory) beanFactory;
            try {
                final WebScript webScript = AnnotationUtils.findAnnotation(Class.forName(beanClassName), WebScript.class);
                if (webScript != null) {
                    String beanName = webScript.value();
                    if (StringUtils.hasText(beanName)) {
                        final String webScriptName = "webscript." + beanName + "." + webScript.method().toString().toLowerCase();
                        factory.removeBeanDefinition(definitionName);
                        factory.registerBeanDefinition(webScriptName, bd);
                    } else {
                        throw new FatalBeanException(String.format("%s is @WebScript annotated, but no value set.", beanClassName));
                    }
                }
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(), e);
                throw new FatalBeanException(e.getMessage(), e);
            }
        } else {
            logger.error(String.format("Unable to register '%s' as webscript because beanFactory is not instance of 'DefaultListableBeanFactory'", definitionName));
        }
    }
}