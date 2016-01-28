package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.Behaviour;
import it.cosenonjaviste.alfresco.annotations.processors.exceptions.ConfigurationAnnotationException;
import org.alfresco.repo.policy.ClassPolicy;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.namespace.NamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

/**
 * <tt>BeanPostProcessor</tt> for {@link Behaviour} annotation.
 *
 * @author Andrea Como
 */
public class BehaviourConfigurer implements BeanPostProcessor {

    private static final Logger LOGGER = Logger.getLogger(BehaviourConfigurer.class);

    private final NamespacePrefixResolver prefixResolver;

    private final PolicyComponent policyComponent;

    BehaviourConfigurer(PolicyComponent policyComponent, NamespacePrefixResolver prefixResolver) {
        this.policyComponent = policyComponent;
        this.prefixResolver = prefixResolver;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ClassPolicy && bean.getClass().getAnnotation(Behaviour.class) != null) {
            Behaviour behaviorAnnotation = bean.getClass().getAnnotation(Behaviour.class);

            Method method = discoverMethodEvent((ClassPolicy) bean);
            org.alfresco.repo.policy.Behaviour behaviour = new JavaBehaviour(bean, method.getName(),
                    behaviorAnnotation.frequency());

            this.policyComponent.bindClassBehaviour(
                    QName.createQName(NamespaceService.ALFRESCO_URI, method.getName()),
                    asQName(behaviorAnnotation.type()),
                    behaviour);
        }

        return bean;
    }

    private QName asQName(String qName) {
        QName resolved = QName.resolveToQName(prefixResolver, qName);
        if (resolved.toString().equals(QName.NAMESPACE_BEGIN + NamespaceService.CONTENT_MODEL_1_0_URI + QName.NAMESPACE_END + qName)) {
            throw new ConfigurationAnnotationException("Unable to convert QName string " + qName);
        } else {
            LOGGER.debug(String.format("QName '%s' resolved", resolved));
            return resolved;
        }
    }

    private Method discoverMethodEvent(ClassPolicy bean) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        if (methods.length == 1) {
            LOGGER.debug(String.format("Policy method '%s' will be registered", methods[0]));
            return methods[0];
        } else {
            throw new ConfigurationAnnotationException("ClassPolicy child class should have just one method!");
        }
    }
}
