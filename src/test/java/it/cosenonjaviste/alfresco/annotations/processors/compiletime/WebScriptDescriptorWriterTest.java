package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import it.cosenonjaviste.alfresco.annotations.WebScriptDescriptor;
import it.cosenonjaviste.alfresco.annotations.constants.AuthenticationType;
import it.cosenonjaviste.alfresco.annotations.constants.FormatType;
import it.cosenonjaviste.alfresco.annotations.constants.TransactionType;
import org.junit.Test;

import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

/**
 * Tests for {@link WebScriptDescriptorWriter#doWrite(Annotation, Writer)}
 *
 * @author Andrea Como
 */
public class WebScriptDescriptorWriterTest {

    @Test
    public void shouldGenerateDefaultDescriptor() {

        TestWebscript1 webscript1 = new TestWebscript1();
        WebScriptDescriptor annotation = webscript1.getClass().getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<shortname>test1</shortname>"));
        assertTrue(descriptor.contains("<description></description>"));
        assertTrue(descriptor.contains("<url><![CDATA[/v1/test]]></url>"));
        assertTrue(descriptor.contains("<authentication>none</authentication>"));
        assertTrue(descriptor.contains("<format default=\"html\">any</format>"));
        assertFalse(descriptor.contains("<transaction>"));
    }

    @Test
    public void shouldGenerateMultipleUri() {

        TestWebscript2 webscript2 = new TestWebscript2();
        WebScriptDescriptor annotation = webscript2.getClass().getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<url><![CDATA[/v1/test]]></url>"));
        assertTrue(descriptor.contains("<url><![CDATA[/v1/test?q={q}]]></url>"));
    }

    @Test
    public void shouldHaveAuthAndRunAsAttribute() {

        TestWebscript3 webscript3 = new TestWebscript3();
        WebScriptDescriptor annotation = webscript3.getClass().getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<authentication runas=\"Joe\">user</authentication>"));
    }

    @Test
    public void shouldHaveJsonDefaultFormat() {

        TestWebscript4 webscript4 = new TestWebscript4();
        WebScriptDescriptor annotation = webscript4.getClass().getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<format default=\"json\">any</format>"));
    }

    @Test
    public void shouldHaveTransactionLevel() {

        TestWebscript5 webscript5 = new TestWebscript5();
        WebScriptDescriptor annotation = webscript5.getClass().getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<transaction>requiresnew</transaction>"));
    }

    private String fillTemplateIntoString(WebScriptDescriptor annotation) {
        StringWriter writer = new StringWriter();
        new WebScriptDescriptorWriter(null).doWrite(annotation, writer);

        return writer.toString();
    }

    @WebScriptDescriptor(shortName = "test1", urls = "/v1/test")
    private static class TestWebscript1 {

    }

    @WebScriptDescriptor(shortName = "test2", urls = {"/v1/test", "/v1/test?q={q}"})
    private static class TestWebscript2 {

    }

    @WebScriptDescriptor(shortName = "test3", urls = {"/v1/test", "/v1/test?q={q}"}, runAs = "Joe", authentication = AuthenticationType.USER)
    private static class TestWebscript3 {

    }

    @WebScriptDescriptor(shortName = "test4", urls = "/v1/test?q={q}", format = FormatType.JSON, authentication = AuthenticationType.USER)
    private static class TestWebscript4 {

    }

    @WebScriptDescriptor(shortName = "test5", urls = "/v1/test?q={q}", transaction = TransactionType.REQUIRES_NEW)
    private static class TestWebscript5 {

    }
}