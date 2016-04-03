package it.cosenonjaviste.alfresco.annotations.workflow;

/**
 * A stereotype on ActivitiBean annotation. It provides an annotation processing checking if class annotated implements TaskListene
 *
 * @author Alberto Rugnone
 */
@ActivitiBean
public @interface TaskListenerBean {
    String value() default "";
}
