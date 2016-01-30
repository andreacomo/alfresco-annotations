package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Logger wrapper on {@link javax.annotation.processing.Messager}
 *
 * @author Andrea Como
 *
 */
class ProcessLog {

    private final ProcessingEnvironment processingEnv;

    public ProcessLog(ProcessingEnvironment processingEnv) {
        super();
        this.processingEnv = processingEnv;
    }

    public void info(String message) {
        log(Diagnostic.Kind.NOTE, message);
    }

    public void warn(String message) {
        log(Diagnostic.Kind.WARNING, message);
    }

    public void error(String message) {
        log(Diagnostic.Kind.ERROR, message);
    }

    public void error(String message, Exception e) {
        log(Diagnostic.Kind.ERROR, message);
        e.printStackTrace();
    }

    public void log(Diagnostic.Kind level, String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }
}
