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
