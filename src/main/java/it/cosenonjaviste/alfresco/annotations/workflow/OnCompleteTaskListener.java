package it.cosenonjaviste.alfresco.annotations.workflow;

import it.cosenonjaviste.alfresco.annotations.ChildOf;

import java.lang.annotation.*;

/**
 *
 * * Stereotype on ActivitiBean annotation. A bean so annotated is also ChildOf activitiCompleteTaskListener
 *
 * @author Alberto Rugnone
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ActivitiBean
@ChildOf("activitiCompleteTaskListener")
public @interface OnCompleteTaskListener {
    /**
     * Bean name
     *
     * @return bean name
     */
    String value() default "";
}
