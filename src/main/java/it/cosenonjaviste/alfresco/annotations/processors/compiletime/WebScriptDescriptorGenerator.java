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
            throw new WebScriptDescriptorGeneratorException(e.getMessage(), e);
        }
    }

    private void createResource(TypeElement classElement) {
        WebScript webScriptAnnotation = classElement.getAnnotation(WebScript.class);
        WebScriptDescriptor descriptor = classElement.getAnnotation(WebScriptDescriptor.class);

        if (webScriptAnnotation != null) {
            String name = extractWebScriptName(webScriptAnnotation.value());
            String path = extractWebScriptPath(webScriptAnnotation.value());
            String descName = name + "." + webScriptAnnotation.method().toString().toLowerCase() + ".desc.xml";
            this.resourceWriter.generateResourceFile(descriptor, descName, path);
        } else {
            throw new WebScriptDescriptorGeneratorException("WebScript annotation must be present with WebScriptDescriptor!");
        }
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
