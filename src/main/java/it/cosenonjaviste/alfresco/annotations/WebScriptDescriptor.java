package it.cosenonjaviste.alfresco.annotations;

import it.cosenonjaviste.alfresco.annotations.constants.AuthenticationType;
import it.cosenonjaviste.alfresco.annotations.constants.FormatType;
import it.cosenonjaviste.alfresco.annotations.constants.FormatTypePosition;
import it.cosenonjaviste.alfresco.annotations.constants.TransactionType;

import java.lang.annotation.*;

/**
 * Stereotype annotation for generating an xml descriptor for webscripts.
 *
 * Must be used with {@link WebScript}
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebScriptDescriptor {

    /**
     * Is a human readable name for the Web Script
     *
     * @return short description
     */
    String shortName();

    /**
     * Is documentation for the Web Script
     *
     * @return long description
     */
    String description() default "";

    /**
     * Webscript url JAX-RS compliant such as:
     *
     * <pre>
     *     /content/a/b/c/d.txt
     *     /sample/helloworld?to={name?}
     *     /blog/search?q={searchTerm}&amp;n={numResults}
     *     /people/{personName}/profile/{profileStyle}
     *     /blog/category/{category}?n={itemsperpage?}
     *     /user/{userid}
     * </pre>
     *
     * @return resource urls
     */
    String[] urls();

    /**
     * Default format, if {@link #formatPosition()} not specified
     *
     * @return default format
     */
    FormatType format() default FormatType.HTML;

    /**
     * Where to specify format in url: controls how the content-type of the response can be specified via the URI
     *
     * @see {@link FormatTypePosition}
     *
     * @return where to specify format
     */
    FormatTypePosition formatPosition() default FormatTypePosition.ANY;

    /**
     * Required level of authentication
     *
     * @return authentication level
     */
    AuthenticationType authentication() default AuthenticationType.NONE;

    /**
     * Force the execution of a webscript as a specific user
     *
     * @return userName
     */
    String runAs() default "";

    /**
     * Specify the required level transaction
     *
     * @see TransactionType
     *
     * @return transaction level
     */
    TransactionType transaction() default TransactionType.DEFAULT;
}
