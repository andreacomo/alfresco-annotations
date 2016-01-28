package it.cosenonjaviste.alfresco.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Stereotype annotation for defining a new webscript controller.
 *
 * Annotated class should extends {@link org.springframework.extensions.webscripts.DeclarativeWebScript}
 *
 * Remember that {@link #value()} must follow this pattern:
 *
 * <pre>
 *     webscript.[path.to.descriptors].[http-method]
 * </pre>
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ChildOf("webscript")
public @interface WebScript {

    String value();

}
