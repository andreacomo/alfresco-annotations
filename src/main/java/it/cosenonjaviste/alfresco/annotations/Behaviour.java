package it.cosenonjaviste.alfresco.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;

/**
 * Stereotype annotation for defining a new behaviour.
 *
 * Annotated class should extends one child of {@link org.alfresco.repo.policy.ClassPolicy}
 *
 * <p>
 * NB: since Alfresco 5.0, <code>Behaviour</code> and <code>BehaviourBean</code> are present
 * </p>
 *
 * @author Andrea Como
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Behaviour {

    String value();

    /**
     * Must be a QName in <code>prefix:localName</code> or <code>{namespaceURI}localName</code> format
     *
     * @return QName as string
     */
    String type();

    NotificationFrequency frequency() default NotificationFrequency.TRANSACTION_COMMIT;

}
