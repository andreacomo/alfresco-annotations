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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

class FreemarkerHelper {

    private static final Logger LOGGER = Logger.getLogger(FreemarkerHelper.class.getName());

    private static FreemarkerHelper helper;

    private final Configuration configuration;

    private FreemarkerHelper() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        configuration
                .setTemplateLoader(new ClassTemplateLoader(this.getClass(), "/META-INF/templates"));
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static synchronized TemplateBuilder fill(String template) {
        if (helper == null) {
            helper = new FreemarkerHelper();
        }
        try {
            return new TemplateBuilder(helper.configuration.getTemplate(template));
        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            throw new IllegalArgumentException(String.format("Template %s not found", template), e);
        }
    }

    public static class TemplateBuilder {

        private final Template template;

        private final Map<String, Object> model = new HashMap<>();

        public TemplateBuilder(Template template) {
            this.template = template;
        }

        public TemplateBuilder with(String key, Object value) {
            model.put(key, value);

            return this;
        }

        public void write(Writer writer) {
            try {
                template.process(model, writer);
            } catch (TemplateException | IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                throw new IllegalArgumentException(e);
            }
        }

    }

}
