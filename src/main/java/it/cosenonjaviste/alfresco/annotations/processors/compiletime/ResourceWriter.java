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
            FileObject resource = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, path, name);
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
