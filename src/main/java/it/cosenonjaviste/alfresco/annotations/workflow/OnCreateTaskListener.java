package it.cosenonjaviste.alfresco.annotations.workflow;

import it.cosenonjaviste.alfresco.annotations.ChildOf;

import java.lang.annotation.*;

/**
 * Stereotype on {@link ActivitiBean} annotation. A bean so annotated is also ChildOf <tt>activitiCreateTaskListener</tt> and
 * should extend {@link org.alfresco.repo.workflow.activiti.tasklistener.TaskCreateListener}
 * <br >
 * Any children of {@link org.alfresco.repo.workflow.activiti.tasklistener.TaskCreateListener}
 * will be notified when a <strong>task is created</strong>
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
