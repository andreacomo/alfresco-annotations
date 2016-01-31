package it.cosenonjaviste.alfresco.annotations.constants;

/**
 * Webscript authentication typology.
 *
 * More details on <a href="https://wiki.alfresco.com/wiki/Web_Scripts#Creating_a_Description_Document">Anfresco Wiki</a>
 *
 * @author Andrea Como
 */
public enum AuthenticationType {

    /**
     * Specifies that no authentication is required at all
     */
    NONE,

    /**
     * Specifies that at least guest authentication is required
     */
    GUEST,

    /**
     * Specifies that at least named user authentication is required
     */
    USER,

    /**
     * Specifies that at least a named admin authentication is required
     */
    ADMIN;

    public String toString() {
        return this.name().toLowerCase();
    }
}
