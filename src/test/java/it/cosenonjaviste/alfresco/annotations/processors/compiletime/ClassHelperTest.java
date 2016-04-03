package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import com.google.testing.compile.CompilationRule;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static org.junit.Assert.assertTrue;

/**
 * Created by albertorugnone on 02/04/16.
 */
public class ClassHelperTest {


    public @Rule  CompilationRule rule = new CompilationRule();
    private Elements elements;
    private Types types;

    @Before
    public void setUp() {
        elements = rule.getElements();
        types = rule.getTypes();

    }

    @Test
    public void hasInterface() throws Exception {
        TypeElement taskListenerToTest = elements.getTypeElement(TaskListenerToTest.class.getCanonicalName());
        final boolean hasInterface = ClassHelper.hasInterface(taskListenerToTest, TaskListener.class);
        assertTrue(hasInterface);
    }

    class TaskListenerToTest implements TaskListener {

        @Override
        public void notify(DelegateTask delegateTask) {

        }
    }
}