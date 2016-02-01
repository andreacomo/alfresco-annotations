package it.cosenonjaviste.alfresco.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to reference an xml-declared value.
 * <p>
 * Behaves like bean-value attribute. All properties are inherited.
 * <p>
 * To enable the corresponding ChildOfConfigurer, add it to your spring configuration. For Example like this:
 * <p>
 * <code>
 * &lt;bean class="it.omniagroup.ecm.internal.utils.ChildOfConfigurer"/&gt;
 * </code>
 *
 * @author Jan Esser
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChildOf {

    /**
     * Parent bean name
     *
     * @return parent bean name
     */
    String value();
}
