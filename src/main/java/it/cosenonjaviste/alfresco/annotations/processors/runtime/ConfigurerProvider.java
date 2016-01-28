package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.namespace.NamespacePrefixResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean factory for configurer classes
 *
 * @author Andrea Como
 */
@Configuration
public class ConfigurerProvider {

    @Autowired
    private PolicyComponent policyComponent;

    @Autowired
    @Qualifier("NamespaceService")
    private NamespacePrefixResolver namespacePrefixResolver;

    @Bean
    public static ChildOfConfigurer childOfConfigurer() {
        return new ChildOfConfigurer();
    }

    @Bean
    public JsExtensionConfigurer jsExtensionConfigurer() {
        return new JsExtensionConfigurer();
    }

    @Bean
    public BehaviourConfigurer behaviourConfigurer() {
        return new BehaviourConfigurer(policyComponent, namespacePrefixResolver);
    }
}
