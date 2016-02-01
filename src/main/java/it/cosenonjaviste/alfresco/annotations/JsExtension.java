package it.cosenonjaviste.alfresco.annotations;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Stereotype annotation for defining a new action.
 *
 * Annotated class must extends {@link org.alfresco.repo.jscript.BaseScopableProcessorExtension}
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ChildOf("baseJavaScriptExtension")
@DependsOn("javaScriptProcessor")
public @interface JsExtension {

    /**
     * Bean name and extension name
     *
     * @return bean name and extension name
     */
    String value();

}
