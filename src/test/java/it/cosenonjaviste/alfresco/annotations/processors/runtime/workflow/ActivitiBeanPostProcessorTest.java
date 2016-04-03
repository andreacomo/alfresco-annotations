package it.cosenonjaviste.alfresco.annotations.processors.runtime.workflow;

import it.cosenonjaviste.alfresco.annotations.processors.runtime.ActivitiBeanPostProcessor;
import it.cosenonjaviste.alfresco.annotations.workflow.ActivitiBean;
import it.cosenonjaviste.alfresco.annotations.workflow.OnCreateTaskListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Mocking Test of ActivitiBeanPostProcessor
 *
 * @author  Alberto Rugnone
 */
@RunWith(MockitoJUnitRunner.class)
public class ActivitiBeanPostProcessorTest {

    @ActivitiBean
    class ActivitiBeanToTest {
        @Override
        public String toString() {
            return "beanNameAsString";
        }
    }
    @ActivitiBean("activitiBeanName")
    class ActivitiBeanWithNameToTest {
        @Override
        public String toString() {
            return "beanWithNameAsString";
        }
    }
    @OnCreateTaskListener("createTaskListenerName")
    class CreateTaskListener {
        @Override
        public String toString() {
            return "createTaskListenerAsString";
        }
    }

    class BeanToTest {
        @Override
        public String toString() {
            return "beanNameAsString";
        }
    }

    @Mock
    ApplicationContext context;

    @InjectMocks
    ActivitiBeanPostProcessor activitiBeanPostProcessor;

    @Test
    public void shouldPostProcessBeforeInitializationAnActivitiBean() throws Exception {
        Map<String, Object> activitiRegistry = new HashMap<>();
        when(context.containsBean("activitiBeanRegistry")).thenReturn(true);
        when(context.getBean("activitiBeanRegistry")).thenReturn(activitiRegistry);


        activitiBeanPostProcessor.postProcessBeforeInitialization(new ActivitiBeanToTest(), "beanName");
        assertEquals("beanNameAsString", activitiRegistry.get("beanName").toString());
    }
    @Test
    public void shouldPostProcessBeforeInitializationAnActivitiBeanWithName() throws Exception {
        Map<String, Object> activitiRegistry = new HashMap<>();
        when(context.containsBean("activitiBeanRegistry")).thenReturn(true);
        when(context.getBean("activitiBeanRegistry")).thenReturn(activitiRegistry);


        activitiBeanPostProcessor.postProcessBeforeInitialization(new ActivitiBeanWithNameToTest(), "beanName");
        assertEquals("beanWithNameAsString", activitiRegistry.get("activitiBeanName").toString());
    }

    @Test
    public void shouldPostProcessBeforeInitializationACreateTaskListener() throws Exception {
        Map<String, Object> activitiRegistry = new HashMap<>();
        when(context.containsBean("activitiBeanRegistry")).thenReturn(true);
        when(context.getBean("activitiBeanRegistry")).thenReturn(activitiRegistry);


        activitiBeanPostProcessor.postProcessBeforeInitialization(new CreateTaskListener(), "beanName");
        assertEquals("createTaskListenerAsString", activitiRegistry.get("createTaskListenerName").toString());
    }

    @Test
    public void shouldPostProcessBeforeInitializationABean() throws Exception {
        Map<String, Object> activitiRegistry = new HashMap<>();
        when(context.containsBean("activitiBeanRegistry")).thenReturn(true);
        when(context.getBean("activitiBeanRegistry")).thenReturn(activitiRegistry);

        activitiBeanPostProcessor.postProcessBeforeInitialization(new BeanToTest(), "beanName");
        assertNull(activitiRegistry.get("beanName"));
    }
}