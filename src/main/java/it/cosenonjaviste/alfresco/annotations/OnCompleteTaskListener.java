package it.cosenonjaviste.alfresco.annotations;

import java.lang.annotation.*;

/**
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
