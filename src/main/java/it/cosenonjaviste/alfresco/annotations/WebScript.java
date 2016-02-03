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
     * WebScript name with (or without) path name. I.e. <tt>myWebScript</tt> or <tt>it.cnj.myWebScript</tt>
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
