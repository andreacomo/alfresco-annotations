package it.cosenonjaviste.alfresco.annotations.constants;

/**
 * Where resource type is specified in URI.
 *
 * More details on <a href="https://wiki.alfresco.com/wiki/Web_Scripts#Creating_a_Description_Document">Anfresco Wiki</a>
 *
 * @author Andrea Como
 */
public enum FormatTypePosition {

    /**
     * The content-type is specified via the format query string parameter, for example <code>/helloworld?to=dave&format=xml</code>
     */
    ARGUMENT,

    /**
     * The content-type is specified via the URI extension, for example <code>/hello/world.xml?to=dave</code>
     */
    EXTENSION,

    /**
     * Defualt value. Either of the above can be used
     */
    ANY;

    public String toString() {
        return this.name().toLowerCase();
    }
}
