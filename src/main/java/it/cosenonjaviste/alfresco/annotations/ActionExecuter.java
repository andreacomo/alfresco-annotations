package it.cosenonjaviste.alfresco.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Stereotype annotation for defining a new action.
 *
 * Annotated class must extends {@link org.alfresco.repo.action.executer.ActionExecuterAbstractBase}
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ChildOf("action-executer")
public @interface ActionExecuter {

    String value();

}
