package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import org.activiti.engine.delegate.TaskListener;
import org.alfresco.util.collections.CollectionUtils;

import javax.lang.model.element.Element;
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
    static boolean hasInterface(TypeElement element, Class<?> cl) {
        if(element == null) return false;
        if(cl == null) return false;
        final List<? extends TypeMirror> interfaces = element.getInterfaces();
        for (TypeMirror anInterface : interfaces) {
            final String canonicalName = cl.getCanonicalName();
            if (canonicalName.equals(anInterface.toString())) {
                return true;
            }
        }
        return false;
    }


    /**
     * check if the TypeElement extends a given class
     * @param element to check if extending superClass
     * @param cl to match
     * @return true if element representes a class extending superclass, false otherwise.
     */
    public static boolean extendsSuperClass(TypeElement element, Class<?> cl) {
        if(cl == null) return false;
        if(element == null) return false;

        final TypeMirror superclass = element.getSuperclass();

        return cl.getCanonicalName().equals(superclass.toString());
    }
}
