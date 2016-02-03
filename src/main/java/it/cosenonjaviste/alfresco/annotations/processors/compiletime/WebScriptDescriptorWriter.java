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

import it.cosenonjaviste.alfresco.annotations.WebScriptDescriptor;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.Writer;

/**
 * Fill template <tt>webscript.desc.xml.ftl</tt> with {@link WebScriptDescriptor} values
 *
 * @author Andrea Como
 */
class WebScriptDescriptorWriter extends ResourceWriter<WebScriptDescriptor> {

    public WebScriptDescriptorWriter(ProcessingEnvironment processingEnv) {
        super(processingEnv);
    }

    @Override
    protected void doWrite(WebScriptDescriptor annotation, Writer writer) {
        FreemarkerHelper.fill("webscript.desc.xml.ftl")
                .with("shortName", annotation.shortName())
                .with("description", annotation.description())
                .with("urls", annotation.urls())
                .with("runAs", annotation.runAs())
                .with("auth", annotation.authentication().toString())
                .with("defaultFormatType", annotation.format().toString())
                .with("formatTypePosition", annotation.formatPosition().toString())
                .with("transaction", annotation.transaction().toString())
                .write(writer);
    }
}
