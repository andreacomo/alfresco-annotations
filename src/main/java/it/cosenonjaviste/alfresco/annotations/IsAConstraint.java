package it.cosenonjaviste.alfresco.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Annotate your bean to make it a constraint for an Action. The bean so annotated will be child-of action-constraint.
 * Applying this annotation is equivalent to xml configuration
 * {@code
 * <bean id="beanId" class="com.example.MyConstraint" parent="action-constraint"/>
 * }
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@ChildOf("action-constraint")
public @interface IsAConstraint {
    /**
     * Bean name
     *
     * @return bean name
     */
    String value();

}
