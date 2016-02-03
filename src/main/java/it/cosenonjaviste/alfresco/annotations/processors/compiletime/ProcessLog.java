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
