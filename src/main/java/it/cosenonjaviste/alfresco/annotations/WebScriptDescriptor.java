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
