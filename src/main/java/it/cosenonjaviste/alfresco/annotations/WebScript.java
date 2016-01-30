package it.cosenonjaviste.alfresco.annotations;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Stereotype annotation for defining a new webscript controller.
 *
 * Annotated class should extends {@link org.springframework.extensions.webscripts.DeclarativeWebScript}
 *
 * No need to remember standard name pattern for webscript!! Just provide:
 *
 * <ul>
 *     <li>{@link #value()}: bean name (may be package + bean name)</li>
 *     <li>{@link #method()}: HTTP method to bind webscript on.</li>
 * </ul>
 *
 * A proper configurer will create webscript name according to Alfresco requirements (<tt>webscript.[name].[http-method]</tt>)
 *
 * @see it.cosenonjaviste.alfresco.annotations.processors.runtime.WebScriptConfigurer
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ChildOf("webscript")
public @interface WebScript {

    /**
     * Webscript name with (or without) patn name. I.e. <tt>myWebScript</tt> or <tt>it.cnj.myWebScript</tt>
     *
     * @return a bean name
     */
    String value();

    /**
     * Http method
     *
     * @return
     */
    HttpMethod method() default HttpMethod.GET;

}
