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

import it.cosenonjaviste.alfresco.annotations.Behaviour;
import it.cosenonjaviste.alfresco.annotations.processors.exceptions.ConfigurationAnnotationException;
import org.alfresco.repo.policy.ClassPolicy;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.namespace.NamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <tt>BeanPostProcessor</tt> for {@link Behaviour} annotation.
 *
 * @author Andrea Como
 */
@Component
public class BehaviourConfigurer implements BeanPostProcessor, ApplicationContextAware{

    private static Log LOGGER = LogFactory.getLog(BehaviourConfigurer.class);

    // Cannot use injection to prevent premature bean creation
    private PolicyComponent policyComponent;

    // Cannot use injection to prevent premature bean creation
    private NamespacePrefixResolver prefixResolver;

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ClassPolicy && bean.getClass().getAnnotation(Behaviour.class) != null) {
            Behaviour behaviorAnnotation = bean.getClass().getAnnotation(Behaviour.class);

            List<Method> methods = discoverEventsMethods((ClassPolicy) bean);

            for (Method method : methods) {
                org.alfresco.repo.policy.Behaviour behaviour = new JavaBehaviour(bean, method.getName(),
                        behaviorAnnotation.frequency());

                this.getPolicyComponent().bindClassBehaviour(
                        QName.createQName(NamespaceService.ALFRESCO_URI, method.getName()),
                        asQName(behaviorAnnotation.type()),
                        behaviour);
            }
        }

        return bean;
    }

    private PolicyComponent getPolicyComponent() {
        if (this.policyComponent == null) {
            this.policyComponent = applicationContext.getBean("policyComponent", PolicyComponent.class);
        }
        return this.policyComponent;
    }

    private QName asQName(String qName) {
        QName resolved = QName.resolveToQName(getPrefixResolver(), qName);
        if (resolved.toString().equals(QName.NAMESPACE_BEGIN + NamespaceService.CONTENT_MODEL_1_0_URI + QName.NAMESPACE_END + qName)) {
            throw new ConfigurationAnnotationException("Unable to convert QName string " + qName);
        } else {
            LOGGER.debug(String.format("QName '%s' resolved", resolved));
            return resolved;
        }
    }

    private NamespacePrefixResolver getPrefixResolver() {
        if (this.prefixResolver == null) {
            this.prefixResolver = applicationContext.getBean("NamespaceService", NamespacePrefixResolver.class);
        }
        return this.prefixResolver;
    }

    private List<Method> discoverEventsMethods(ClassPolicy bean) {
        List<Method> methods = new ArrayList<>();
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                if (ClassPolicy.class.isAssignableFrom(anInterface)) {
                    methods.addAll(Arrays.asList(anInterface.getDeclaredMethods()));
                }
            }
        }

        return methods;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
