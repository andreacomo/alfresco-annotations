package it.cosenonjaviste.alfresco.annotations.processors.exceptions;

/**
 * @author Andrea Como
 */
public class ConfigurationAnnotationException extends RuntimeException {

    public ConfigurationAnnotationException() {
    }

    public ConfigurationAnnotationException(String message) {
        super(message);
    }

    public ConfigurationAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationAnnotationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationAnnotationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
