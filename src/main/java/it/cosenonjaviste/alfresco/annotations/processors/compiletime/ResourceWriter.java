package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;

/**
 * Generates a new resource
 *
 * @author Andrea Como
 */
abstract class ResourceWriter<T extends Annotation> {

    private final ProcessingEnvironment processingEnv;

    private final ProcessLog log;

    public ResourceWriter(ProcessingEnvironment processingEnv) {
        super();
        this.processingEnv = processingEnv;
        this.log = new ProcessLog(processingEnv);
    }

    public void generateResourceFile(final T annotation, final String name, final String path) {
        try {
            FileObject resource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, path, name);
            try (Writer writer = resource.openWriter()) {

                doWrite(annotation, writer);

                log.info(String.format("%s created", name));
            }
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    protected abstract void doWrite(T annotation, Writer writer);
}
