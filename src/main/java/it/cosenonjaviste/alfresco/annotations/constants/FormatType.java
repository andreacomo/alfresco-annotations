package it.cosenonjaviste.alfresco.annotations.constants;

/**
 * Resources format types.
 *
 * More details on <a href="https://wiki.alfresco.com/wiki/Web_Scripts#Creating_a_Description_Document">Anfresco Wiki</a>
 *
 * @author Andrea Como
 */
public enum FormatType {

    HTML,
    JSON,
    XML,

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
