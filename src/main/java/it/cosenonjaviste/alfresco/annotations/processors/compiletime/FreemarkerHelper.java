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
