package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean factory for configurer classes
 *
 * @author Andrea Como
 */
@Configuration
public class ConfigurerProvider {

    @Bean
    public ChildOfConfigurer childOfConfigurer() {
        return new ChildOfConfigurer();
    }

    @Bean
    public JsExtensionConfigurer jsExtensionConfigurer() {
        return new JsExtensionConfigurer();
    }
}
