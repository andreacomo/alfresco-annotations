package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import org.apache.commons.lang3.ClassUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * @author Alberto Rugnone
 */
class ClassHelper {

    /**
     * @param element to check if has interface cl
     * @param cl      interface to check
     * @return true if element has cl as interface, false otherwise
     */
    static boolean hasInterface(Element element, Class<?> cl) {
        final List<? extends TypeMirror> interfaces = ((TypeElement) element).getInterfaces();
        for (TypeMirror anInterface : interfaces) {
            final String canonicalName = cl.getCanonicalName();
            if (canonicalName.equals(anInterface.toString())) {
                return true;
            }
        }
        return false;
    }
}
