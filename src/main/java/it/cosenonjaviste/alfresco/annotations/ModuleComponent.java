/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
