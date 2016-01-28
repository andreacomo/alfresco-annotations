package it.cosenonjaviste.alfresco.annotations.processors.runtime.testclasses;

import it.cosenonjaviste.alfresco.annotations.Behaviour;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Andrea Como
 */

public class ClassPolicyForTest {

    @Behaviour(value = "updateProperties", type = "cnj:content")
    public static class UpdateProperties implements NodeServicePolicies.OnUpdatePropertiesPolicy {

        @Override
        public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {

        }
    }
}
