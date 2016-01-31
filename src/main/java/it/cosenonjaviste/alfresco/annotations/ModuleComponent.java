package it.cosenonjaviste.alfresco.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.util.List;

/**
 * Stereotype annotation for defining a new
 * <a href="http://docs.alfresco.com/5.1/references/dev-extension-points-module-component.html">Module Component</a>.
 *
 * Annotated class <strong>must</strong> extends {@link org.alfresco.repo.module.AbstractModuleComponent}
 * otherwise will not be registered
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ChildOf("module.baseComponent")
public @interface ModuleComponent {

    /**
     * The globally unique module name. MUST be set to <strong>module name</strong>
     * (<tt>module.id</tt> parameter from <tt>module.properties</tt>), usually <tt>artifactId</tt> in Maven projects
     *
     * @return module identifier
     */
    String moduleId();

    /**
     * Component bean name
     *
     * @return component name
     */
    String name();

    /**
     * Component description
     *
     * @return component description
     */
    String description() default "";

    /**
     * Set the version number for which this component was added.
     *
     * @return module version which this component was created for
     */
    String sinceVersion();

    /**
     * Set the minimum module version number to which this component applies. Default is <tt>0.0</tt>
     *
     * @return minimum module version required
     */
    String appliesFromVersion() default "0.0";

    /**
     * Determinate if this component should be executed on each startup. Default is <code>true</code>
     *
     * @return if component should be executed each startup
     */
    boolean executeOnceOnly() default true;

    /**
     * Array of <tt>ModuleComponent</tt> {@link #name()} to execute before this component
     *
     * @return array of module component identifiers
     */
    String[] dependsOn() default {};

}
