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
@SupportedSourceVersion(SourceVersion.RELEASE_8)
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
            log.error("Error during descriptor generation. " + e.getMessage(), e);
            throw new WebScriptDescriptorGeneratorException(e);
        }
    }

    private void createResource(TypeElement classElement) {
        WebScript webScriptAnnotation = classElement.getAnnotation(WebScript.class);
        WebScriptDescriptor descriptor = classElement.getAnnotation(WebScriptDescriptor.class);

        String name = extractWebScriptName(webScriptAnnotation.value());
        String path = extractWebScriptPath(webScriptAnnotation.value());
        String descName = name + "." + webScriptAnnotation.method().toString().toLowerCase() + ".desc.xml";
        this.resourceWriter.generateResourceFile(descriptor, descName, path);
    }

    private String extractWebScriptPath(String value) {
        if (value.contains(".")) {
            return value.substring(0, value.lastIndexOf("."));
        } else {
            return "";
        }
    }

    private String extractWebScriptName(String value) {
        if (value.contains(".")) {
            return value.substring(value.lastIndexOf(".") + 1, value.length());
        } else {
            return value;
        }
    }
}
