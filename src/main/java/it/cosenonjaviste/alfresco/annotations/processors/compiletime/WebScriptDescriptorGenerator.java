package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import it.cosenonjaviste.alfresco.annotations.WebScript;
import it.cosenonjaviste.alfresco.annotations.WebScriptDescriptor;
import it.cosenonjaviste.alfresco.annotations.processors.exceptions.WebScriptDescriptorGeneratorException;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author Andrea Como
 */
@SupportedAnnotationTypes("it.cosenonjaviste.alfresco.annotations.WebScriptDescriptor")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class WebScriptDescriptorGenerator extends AbstractProcessor {

    private ProcessLog log;

    private ResourceWriter resourceWriter;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.log = new ProcessLog(processingEnv);
        this.resourceWriter = new WebScriptDescriptorWriter(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            Set<? extends Element> elementsAnnotated = roundEnv.getElementsAnnotatedWith(WebScriptDescriptor.class);
            for (Element element : elementsAnnotated) {
                if (element.getKind() == ElementKind.CLASS) {
                    createResource((TypeElement) element);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("Errore durante la generazione del descrittore", e);
            throw new WebScriptDescriptorGeneratorException(e);
        }
    }

    private void createResource(TypeElement classElement) {
        WebScript webScriptAnnotation = classElement.getAnnotation(WebScript.class);
        String value = webScriptAnnotation.value();
        //String path = extractWebScriptPath(value);

        WebScriptDescriptor descriptor = classElement.getAnnotation(WebScriptDescriptor.class);
        //this.resourceWriter.generateResourceFile(descriptor, );
    }

    /**
     * Extract path and name. Ex: <tt>webscript.it.cnj.hello.get</tt> => <tt>it.cnj.hello</tt>
     *
     * @param value webscript name
     * @return      path and name extracted from name
     */
    private String extractWebScriptPathAndName(String value) {
        return value.substring(value.indexOf("."), value.lastIndexOf("."));
    }
}
