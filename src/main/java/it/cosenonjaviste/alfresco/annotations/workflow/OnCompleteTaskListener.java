package it.cosenonjaviste.alfresco.annotations.workflow;

import it.cosenonjaviste.alfresco.annotations.ChildOf;

import java.lang.annotation.*;

/**
 *
 * Stereotype on {@link ActivitiBean} annotation. A bean so annotated is also ChildOf <tt>activitiCompleteTaskListener</tt> and
 * should extend {@link org.alfresco.repo.workflow.activiti.tasklistener.TaskCompleteListener}
 * <br >
 * Any children of {@link org.alfresco.repo.workflow.activiti.tasklistener.TaskCompleteListener}
 * will be notified when a <strong>task is completed</strong>
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
