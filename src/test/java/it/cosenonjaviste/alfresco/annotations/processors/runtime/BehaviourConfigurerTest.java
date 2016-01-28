package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.processors.runtime.testclasses.ClassPolicyForTest;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.service.namespace.NamespacePrefixResolver;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author Andrea Como
 */
@RunWith(MockitoJUnitRunner.class)
public class BehaviourConfigurerTest {

    private static final String NAMESPACE = "http://www.cosenonjaviste.it/model";

    private static final String PREFIX = "cnj";

    @Mock
    private PolicyComponent policyComponent;

    @Mock
    private NamespacePrefixResolver prefixResolver;

    @InjectMocks
    private BehaviourConfigurer configurer;

    @Test
    public void testPostProcessAfterInitialization() throws Exception {
        when(prefixResolver.getNamespaceURI(PREFIX)).thenReturn(NAMESPACE);

        configurer.postProcessAfterInitialization(new ClassPolicyForTest.UpdateProperties(), "updateProperties");

        verify(prefixResolver).getNamespaceURI(PREFIX);
        verify(policyComponent).bindClassBehaviour(
                eq(QName.createQName(NamespaceService.ALFRESCO_URI, "onUpdateProperties")),
                eq(QName.createQName(NAMESPACE, "content")),
                Matchers.<org.alfresco.repo.policy.Behaviour>any());
    }
}