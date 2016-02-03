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
 * Resources format types.
 *
 * More details on <a href="https://wiki.alfresco.com/wiki/Web_Scripts#Creating_a_Description_Document">WebScript</a>
 * and <a href="https://wiki.alfresco.com/wiki/3.0_Web_Scripts_Framework#HTTP_Response_Formats">WebScript Framework</a> on Alfresco Wiki
 *
 * @author Andrea Como
 */
public enum FormatType {

    /**
     * text/html
     */
    HTML,

    /**
     * text/plain
     */
    TEXT,

    /**
     * text/xml
     */
    XML,

    /**
     * application/atom+xml
     */
    ATOM,

    /**
     * application/atom+xml;type=entry
     */
    ATOMENTRY,

    /**
     * application/atom+xml;type=feed
     */
    ATOMFEED,

    /**
     * application/rss+xml
     */
    RSS,

    /**
     * application/json
     */
    JSON,

    /**
     * application/opensearchdescription+xml
     */
    OPENSEARCHDESCRIPTION,

    /**
     * text/plain (Media Wiki markup)
     */
    MEDIAWIKI,

    /**
     * text/html (head & tail chopped)
     */
    PORTLET,

    /**
     * text/html
     */
    FBML,

    /**
     * text/html
     */
    PHP,

    /**
     * text/javascript
     */
    JS,

    /**
     * text/calendar
     */
    CALENDAR,

    /**
     * In some cases, a URI may decide upon a response content-type at runtime.
     * For these URIs, specify an empty format, for example <code>format default=""</code>
     *
     * <br>
     * Often used when a stream is returned from server
     */
    EMPTY {
        @Override
        public String toString() {
            return "";
        }
    };

    public String toString() {
        return this.name().toLowerCase();
    }
}
