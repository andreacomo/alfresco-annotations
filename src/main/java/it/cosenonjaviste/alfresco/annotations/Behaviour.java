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

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;

/**
 * Stereotype annotation for defining a new behaviour.
 *
 * Annotated class should extends one child of {@link org.alfresco.repo.policy.ClassPolicy}
 *
 * <p>
 * NB: since Alfresco 5.0, <code>Behaviour</code> and <code>BehaviourBean</code> are present
 * </p>
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@DependsOn({"policyComponent", "NamespaceService"})
public @interface Behaviour {

    /**
     * Bean name
     *
     * @return bean name
     */
    String value();

    /**
     * Must be a QName in <code>prefix:localName</code> or <code>{namespaceURI}localName</code> format
     *
     * @return QName as string
     */
    String type();

    /**
     * Defines when trigger this behaviour. Default is {@link NotificationFrequency#TRANSACTION_COMMIT}
     *
     * @see NotificationFrequency
     *
     * @return notification frequency
     */
    NotificationFrequency frequency() default NotificationFrequency.TRANSACTION_COMMIT;

}
