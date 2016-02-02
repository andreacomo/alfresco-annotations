package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.ModuleComponent;
import org.alfresco.repo.module.AbstractModuleComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <tt>BeanPostProcessor</tt> for {@link ModuleComponent} annotation.
 *
 * @author Andrea Como
 */
@Component
public class ModuleComponentConfigurer implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractModuleComponent && bean.getClass().getAnnotation(ModuleComponent.class) != null) {
            ModuleComponent componentAnnotation = bean.getClass().getAnnotation(ModuleComponent.class);

            AbstractModuleComponent component = (AbstractModuleComponent) bean;
            component.setModuleId(componentAnnotation.moduleId());
            component.setName(componentAnnotation.name());
            component.setDescription(componentAnnotation.description());
            component.setSinceVersion(componentAnnotation.sinceVersion());
            component.setAppliesFromVersion(componentAnnotation.appliesFromVersion());
            component.setExecuteOnceOnly(componentAnnotation.executeOnceOnly());

            if (componentAnnotation.dependsOn().length > 0) {
                for (String dependOn : componentAnnotation.dependsOn()) {
                    org.alfresco.repo.module.ModuleComponent moduleComponent = applicationContext.getBean(dependOn, org.alfresco.repo.module.ModuleComponent.class);
                    component.getDependsOn().add(moduleComponent);
                }
            }
        }

        return bean;
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
