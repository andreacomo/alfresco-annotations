package it.cosenonjaviste.alfresco.annotations.processors.runtime.testclasses;

import it.cosenonjaviste.alfresco.annotations.Behaviour;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Andrea Como
 */

public class ClassPolicyMocks {

    public interface AnotherInternface {

        void anInterfaceMethod();
    }

    @Behaviour(value = "updateProperties", type = "cnj:content")
    public static class UpdateProperties implements NodeServicePolicies.OnUpdatePropertiesPolicy {

        @Override
        public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {

        }
    }

    @Behaviour(value = "createDeleteNode", type = "{http://www.cosenonjaviste.it/model}content")
    public static class CreateDeleteNode implements NodeServicePolicies.OnCreateNodePolicy, NodeServicePolicies.OnDeleteNodePolicy, AnotherInternface, Serializable {


        @Override
        public void onCreateNode(ChildAssociationRef childAssocRef) {

        }

        @Override
        public void onDeleteNode(ChildAssociationRef childAssocRef, boolean isNodeArchived) {

        }

        private void aPrivateMethod() {

        }

        public void aPublicMethod() {

        }

        @Override
        public void anInterfaceMethod() {

        }
    }
}
