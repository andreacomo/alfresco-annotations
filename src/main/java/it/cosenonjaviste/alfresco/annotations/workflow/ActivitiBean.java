package it.cosenonjaviste.alfresco.annotations.workflow;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * A bean annotated ActivitiBean will be registered into activiti-bean registry.
 *
 * @author Alberto Rugnone
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
@DependsOn("activitiBeanRegistry")
public @interface ActivitiBean {
    /**
     * Bean name
     *
     * @return bean name
     */
    String value() default "";

}