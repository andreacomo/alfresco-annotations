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
package it.cosenonjaviste.alfresco.annotations.constants;


/**
 * Transactions types.
 *
 * More details on <a href="https://wiki.alfresco.com/wiki/Web_Scripts#Creating_a_Description_Document">Anfresco Wiki</a>
 *
 * @author Andrea Como
 */
public enum TransactionType {

    /**
     * Specifies that no transaction is required at all.
     *
     * <p>
     *     It is default value if {@link AuthenticationType#NONE}
     * </p>
     */
    NONE,

    /**
     * Specifies that a transaction is required (and will inherit an existing transaction, if open)
     *
     * <p>
     *     It is default value if {@link AuthenticationType} IS NOT {@link AuthenticationType#NONE}
     * </p>
     */
    REQUIRED,

    /**
     * Specifies that a new transaction is required
     */
    REQUIRES_NEW {
        @Override
        public String toString() {
            return "requiresnew";
        }
    },

    /**
     * Do not specify transaction type so:
     *
     * <ul>
     *     <li>it's {@link #NONE} if {@link AuthenticationType#NONE}</li>
     *     <li>else {@link #REQUIRED}</li>
     * </ul>
     *
     */
    DEFAULT {
        @Override
        public String toString() {
            return "";
        }
    };

    public String toString() {
        return this.name().toLowerCase();
    }
}
