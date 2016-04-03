package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import com.google.common.collect.ImmutableSet;
import it.cosenonjaviste.alfresco.annotations.workflow.OnCompleteTaskListener;
import it.cosenonjaviste.alfresco.annotations.workflow.TaskListenerBean;
import org.activiti.engine.delegate.TaskListener;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.Set;

/**
 * Activiti TaskListenerBean annotation validator.
 *
 * It validates a class annotated with annotation TaskListenerBean: that class is valid if it implements interface TaskListener.
 *
 * If not the processor throw an a mandatory warning.
 *
 * Note: The class stop the compilation because TaskListener it meant to be used to register an activiti bean which is a org.activiti.engine.delegate.askListener
 *
 * @author Alberto Rugnone
 */
public class TaskListenerValidator<T> extends AbstractProcessor {

    private ProcessLog logger;

    /**
     *
     * {@inheritDoc}
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        logger = ProcessLog.getLogger(processingEnv);
    }

    /**
     * {@inheritDoc}
     *
     * @param annotations to be processed
     * @param roundEnv    on which processing works
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        logger.info(getClass().getName() + " starts annotation processing");
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(TaskListenerBean.class)) {

            if (!ClassHelper.hasInterface(annotatedElement, TaskListener.class)) {
                logger.error("only classes implementing " + TaskListener.class + " can be annotated as " + TaskListenerBean.class.getName(), annotatedElement);
            }

        }
        logger.info(getClass().getName() + " ends annotation processing");
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return last version supported
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * {@inheritDoc}
     *
     * @return the set of supported annotation types
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(TaskListenerBean.class.getCanonicalName());
    }
}
