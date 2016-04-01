package it.cosenonjaviste.alfresco.annotations;

import java.lang.annotation.*;

/**
 *
 * @author Alberto Rugnone
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ActivitiBean
@ChildOf("activitiCreateTaskListener")
public @interface OnCreateTaskListener {
    /**
     * Bean name
     *
     * @return bean name
     */
    String value() default "";
}
