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
