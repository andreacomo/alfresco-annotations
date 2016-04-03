package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import com.google.testing.compile.CompilationRule;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.alfresco.repo.workflow.activiti.tasklistener.TaskCompleteListener;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static org.junit.Assert.assertFalse;
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
    public void shouldHasInterface() throws Exception {
        TypeElement taskListenerToTest = elements.getTypeElement(TaskListenerToTest.class.getCanonicalName());
        final boolean hasInterface = ClassHelper.hasInterface(taskListenerToTest, TaskListener.class);
        assertTrue(hasInterface);
    }
    @Test
    public void shouldDoNotHasNullInterface() throws Exception {
        TypeElement taskListenerToTest = elements.getTypeElement(TaskListenerToTest.class.getCanonicalName());
        final boolean hasInterface = ClassHelper.hasInterface(taskListenerToTest, null);
        assertFalse(hasInterface);
    }
    @Test
    public void shouldNullHasNoInterface() throws Exception {
        final boolean hasInterface = ClassHelper.hasInterface(null, TaskListener.class);
        assertFalse(hasInterface);
    }

    @Test
    public void shouldExtendsSuperClass() throws Exception {
        TypeElement taskCompleteListenerToTest = elements.getTypeElement(TaskCompleteListenerToTest.class.getCanonicalName());
        final boolean extendsSuperClass = ClassHelper.extendsSuperClass(taskCompleteListenerToTest, TaskCompleteListener.class);
        assertTrue(extendsSuperClass);
    }
    @Test
    public void shouldDoNotExtendsNullSuperClass() throws Exception {
        TypeElement taskCompleteListenerToTest = elements.getTypeElement(TaskCompleteListenerToTest.class.getCanonicalName());
        final boolean extendsSuperClass = ClassHelper.extendsSuperClass(taskCompleteListenerToTest, null);
        assertFalse(extendsSuperClass);
    }
    @Test
    public void shouldNullExtendNothing() throws Exception {
        final boolean extendsSuperClass = ClassHelper.extendsSuperClass(null, TaskCompleteListener.class);
        assertFalse(extendsSuperClass);
    }

    class TaskListenerToTest implements TaskListener {

        @Override
        public void notify(DelegateTask delegateTask) {

        }
    }

    class TaskCompleteListenerToTest extends TaskCompleteListener {
    }
}