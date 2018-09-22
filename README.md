<img src="src/main/resources/META-INF/resources/images/aa.png" width="120" align="right"/>

# Alfresco Annotations
Spring stereotype annotations for simplifying development with Alfresco

[![Released Version](https://img.shields.io/maven-central/v/it.cosenonjaviste/alfresco-annotations.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22it.cosenonjaviste%22%20a%3A%22alfresco-annotations%22)

### CI Health

* master

     [![Build Status](https://travis-ci.org/andreacomo/alfresco-annotations.svg?branch=master)](https://travis-ci.org/andreacomo/alfresco-annotations)

* develop

     [![Build Status](https://travis-ci.org/andreacomo/alfresco-annotations.svg?branch=develop)](https://travis-ci.org/andreacomo/alfresco-annotations)

## Why
Tired of compiling boring Spring xml for defining Alfresco beans? Always forget what's the Java-based WebScripts' naming convention or the Action Executer parent bean name?
Then this project is for you!

## Supported Platforms
Tested on:

* Alfresco Community 4.2.c
* Alfresco Community 4.2.d
* Alfresco Community 4.2.e
* Alfresco Community 4.2.f
* Alfresco Community 5.0.c
* Alfresco Community 5.0.d
* Alfresco Community 5.1.x
* Alfresco Community 5.2.x
* Alfresco Enterprise 5.2.x (ACS)

May works on other versions. Please edit this file and pull-request if works on other versions.

## Where is the magic?

There is no magic! This project is simply a collection of **Spring stereotypes annotations**, some **Spring lifecycle processors** to do boilerplate code for you and an **annotation processor** to stop writing webscript xml descriptor!
Just add it to your **REPO** project dependencies:

```xml
<dependency>
  <groupId>it.cosenonjaviste</groupId>
  <artifactId>alfresco-annotations</artifactId>
  <version>1.0.0</version>
</dependency>
```

enable *annotation discovery by package* in your <tt>module-context.xml</tt> (or any other config file you prefer):

```xml
<?xml version='1.0' encoding='UTF-8'?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd">

    <context:component-scan base-package="my.module.base.package" />
</beans>
```


and there you go! If you want annotation processor to write webscript descriptors for you, 
just set *where to write them* on *Maven Compiler Plugin*, for example in target folder *where they are expected*:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <generatedSourcesDirectory>${project.build.directory}/${project.build.finalName}/config/alfresco/extension/templates/webscripts</generatedSourcesDirectory>
    </configuration>
</plugin>
```

If you are using `Alfresco SDK 3`, *destination folder* (`generatedSourcesDirectory`) will be different and likely will be like this:

```xml
...
<generatedSourcesDirectory>${project.build.directory}/classes/alfresco/extension/templates/webscripts</generatedSourcesDirectory>
...
```

## Annotations

Each annotation's javadoc helps you to remember which class you should or must extend, what annotation attributes you must provide and what are default values

* [@ActionExecuter](#actionexecuter)
* [@Behaviour](#behaviour)
* [@JsExtension](#jsextension)
* [@ModuleComponent](#modulecomponent)
* [@WebScript](#webscript)
  * [@WebScriptDescriptor](#webscriptdescriptor)
* [@IsAConstraint](#isaconstraint)
* Workflows:
  * [@ActivitiBean](#activitibean)
  * [@TaskListenerBean](#tasklistenerbean)
  * [@OnCreateListenerBean](#oncreatelistenerbean)
  * [@OnCompleteTaskListener](#onCompletetasklistener)

### @ActionExecuter
##### From
Creating a **Java Action** it was a matter of writing a class which extends ```ActionExecuterAbstractBase``` 
```java
public class MyAction extends ActionExecuterAbstractBase {

  @Override
  protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
    // my action code
  }

  @Override
  protected void addParameterDefinitions(List<ParameterDefinition> paramList) {

  }
}
```
and an xml bean declaration with parent **action-executer**:
```xml
<bean id="myAction"
      class="it.cosenonjaviste.annotations.MyAction"
      parent="action-executer">
    
</bean>
```
##### To
Now, just annotate your class and that's all! Forget about bean parent name, [```@ActionExecuter```](src/main/java/it/cosenonjaviste/alfresco/annotations/ActionExecuter.java) will take care of it!

```java
@ActionExecuter("myAction")
public class MyAction extends ActionExecuterAbstractBase {

  @Override
  protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
    // my action code
  }

  @Override
  protected void addParameterDefinitions(List<ParameterDefinition> paramList) {

  }
}
```
And if you don't remember parent class you must extends, annotation's javadoc will remind you.

### @Behaviour
##### From
Remembering how to register a behavior is always annoying. You have to write a class implementing one or more  ```ClassPolicy``` children and register for which *content type* trigger it, for example:
```java
public class MyBehavior implements NodeServicePolicies.OnUpdatePropertiesPolicy {

  private PolicyComponent policyComponent;

  public void init() {
    Behaviour onUpdateProperties = new JavaBehaviour(this, "onUpdateProperties",
            Behaviour.NotificationFrequency.TRANSACTION_COMMIT);

    this.policyComponent.bindClassBehaviour(QName.createQName(
                    NamespaceService.ALFRESCO_URI, "onUpdateProperties"), ContentModel.TYPE_CONTENT,
            this.onUpdateProperties);
  }

  @Override
  public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {
    // do stuff on properties update for cm:content
  }
}
```
Then write xml bean definition:
```xml
<bean id="myBehaviour" class="it.cosenonjaviste.annotations.MyBehavior" init-method="init">

</bean>
```
##### To
Stop remembering messy **Behaviour** policy registration: just annotate your bean like that:

```java
@Behaviour(value = "myBehaviour", type = "cm:content")
public class MyBehavior implements NodeServicePolicies.OnUpdatePropertiesPolicy {

  @Override
  public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {
    // do stuff on properties update for cm:content
  }
}
```
and thre you go, ready to behave! [```BehaviourConfigurer```](src/main/java/it/cosenonjaviste/alfresco/annotations/processors/runtime/BehaviourConfigurer.java) will take care of registering behaviour methods to *PolicyComponent*.

Since Alfresco 5, you can choose to use standard annotations ```org.alfresco.repo.policy.annotation.Behaviour``` and ```org.alfresco.repo.policy.annotation.BehaviourBean```.

### @JsExtension
JavaScript API is easy and succinct in Alfresco, always powerful but sometimes not enought! 
##### From
To plug a new feature in from Java you have to extends ```BaseScopableProcessorExtension``` or ```BaseProcessorExtension```:

```java
public class HelloExtension extends BaseScopableProcessorExtension {

  public String sayIt() {
    return "Hello";
  }
}
```
and register it in context xml file along with bean parent name **baseJavaScriptExtension** and providing an *extensionName*:

```xml
<bean id="helloExtension"
      class="it.cosenonjaviste.annotations.HelloExtension"
      parent="baseJavaScriptExtension">
    <property name="extensionName">
        <value>hello</value>
    </property>
</bean>
```
##### To
Now it's easier: annotate your bean

```java
@JsExtension("hello")
public class HelloExtension extends BaseScopableProcessorExtension {

  public String sayIt() {
    return "Hello";
  }
}
```

then access your new service in JavaScript like this:

```javascript
logger.log(hello.sayIt());
```
[```JsExtensionConfigurer```](src/main/java/it/cosenonjaviste/alfresco/annotations/processors/runtime/JsExtensionConfigurer.java) will register bean name as **Extension** name.

### @ModuleComponent
**Module Components** are special beans to be aimed at single execution for a specific AMP version, useful for initialization/update purpose.

Module Components require a bean extending ```AbstractModuleComponent``` 
```java
public class HelloComponent extends AbstractModuleComponent {

  @Override
  protected void executeInternal() throws Throwable {
    // execute on startup
  }
}
```
a bunch of xml configuration attributes and a bean parent **module.baseComponent** a such as:

```xml
<bean id="myComponent" class="it.cosenonjaviste.annotations.HelloComponent" parent="module.baseComponent">
    <property name="moduleId" value="my-module-id" />
    <property name="name" value="myComponent" />
    <property name="description" value="A description" />
    <property name="sinceVersion" value="1.0.0" />
</bean>
```
##### To
Now you can achieve all of this through annotation parameters:

```java
@ModuleComponent(moduleId = "my-module-id", name = "myComponent", sinceVersion = "1.0.0")
public class HelloComponent extends AbstractModuleComponent {

  @Override
  protected void executeInternal() throws Throwable {
    // execute on startup
  }
}
```
and [```ModuleComponentConfigurer```](src/main/java/it/cosenonjaviste/alfresco/annotations/processors/runtime/ModuleComponentConfigurer.java) will set these values to bean instance.

Take care of ```moduleId```: it *must correspond* to your **module ID**, defined by *module.id* property in ```module.properties``` file of your AMP. Usually corresponds to Maven *artifactId*.

### @WebScript
WebScript are the most used feature in Alfresco, allowing access to repository feature in a RESTful way. 

##### From
For creating a new Java-backed WebScript you can extends ```DeclarativeWebScript``` (or one of its parent)
```java
public class MyWebScript extends DeclarativeWebScript {

  @Override
  protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
    Map<String, Object> model = new HashMap<>();
    // do stuff
    return model;
  }
}
```
and follow a *specific naming convention* along with parent bean **webscript** while registering on xml context file:
```xml
<bean id="webscript.it.cnj.myWebscript.post"
      class="it.cosenonjaviste.annotations.MyWebscript"
      parent="webscript">
</bean>
```
##### To
Remembering Java-backed WebScript controller naming convention is pretty boring. I'd like to set a bean name and an HTTP method to define a new WebScript such as:

```java
@WebScript(value = "it.cnj.myWebscript", method = HttpMethod.POST)
public class MyWebScript extends DeclarativeWebScript {

  @Override
  protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
    Map<String, Object> model = new HashMap<>();
    // do stuff
    return model;
  }
}
```

If ```method``` is not specified, ```HttpMethod.GET``` is assumed as default.

#### @WebScriptDescriptor
The most annoying thing to deal with WebScripts is to write **xml descriptor**. If you registered ```<generatedSourcesDirectory>``` in ```maven-compiler-plugin```, a class annotated like this:

```java
@WebScript("myWebScript")
@WebScriptDescriptor(shortName = "my-webscript", urls = "/v1/my-webscript", format = FormatType.JSON, authentication = AuthenticationType.USER)
public class MyWebScript extends DeclarativeWebScript {

  @Override
  protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
    Map<String, Object> model = new HashMap<>();
    // do stuff
    return model;
  }
}
```

will generate ```myWebScript.get.desc.xml``` for you **at compile time** through [```WebScriptDescriptorGenerator```](src/main/java/it/cosenonjaviste/alfresco/annotations/processors/compiletime/WebScriptDescriptorGenerator.java):

```xml
<webscript>
  <shortname>hello-again</shortname>
  <description></description>
  <url>/v1/my-webscript</url>
  <authentication>user</authentication>
  <format default="json">any</format>
</webscript>
```

It's up to you now to complete MVC with ```myWebScript.get.json.ftl``` file.

Right now this annotation does not support some advanced descriptor features such as *family*, *cache*, *negotiate*, *kind* and *lifecycle*.

#### @IsAConstraint
Creating programmatically an Action *expecting parameters from client* requires you to register your ParameterDefinition

```java
new ParameterDefinitionImpl(name,
                    definitionType,
                    isMandatory,
                    displayLabel,
                    isMultiValued,
                    constraintBeanName);
```
that may need a *constraint class* on parameter value specified by bean name ```constraintBeanName```.

Such a constraint is a bean extending ```BaseParameterConstraint``` abstract class (as reminded by annotation's javadoc):

```java
public class MyConstraint implements BaseParameterConstraint {
...
}
```

##### From
So far, after implementing the constraint, you have to declare in your xml a bean with parent the bean *action-constraint*.
```xml
<bean id="myConstraint" class="com.example.MyConstraint" parent="action-constraint"/>
```

##### To
```@IsAConstraint``` is a convenient and meaningful stereotype to shortcut the following set of annotation

```java
 @Component
 @ChildOf("action-constraint")
```

So, you need just this annotation on your bean with its bean name
```java
@IsAConstraint("myConstraint")
public class MyConstraint implements BaseParameterConstraint {
...
}
```

### Workflows

#### @ActivitiBean
Working with workflow can be cumbersome.
If you have to add a java based listener and inject spring bean into that it can be even more.

##### From
One solution is based on the following xml:

```xml
<bean id="my-task-listener"
    class="it.cnj.MyTaskListener">
</bean>


<bean id="my.activitiBeanRegistry"
    class="org.springframework.beans.factory.config.MethodInvokingFactoryBean"
    depends-on="activitiBeanRegistry">

    <property name="targetObject">
        <ref bean="activitiBeanRegistry" />
    </property>

    <property name="targetMethod" value="putAll" />

    <property name="arguments">
        <map>
             <entry key="customTaskListener" value-ref="my-task-listener" />
        </map>
    </property>
</bean>
```

then in your workflow task you have to add the listener as delegate, for example on complete event

```xml
 <userTask id="usertask1" name="User Task" activiti:candidateGroups="GROUP_ALFRESCO_ADMINISTRATOR" activiti:formKey="wf:adhocTask">
  <extensionElements>
    <activiti:taskListener event="complete" delegateExpression="${customTaskListener}"></activiti:taskListener>
  </extensionElements>
 </userTask>
```

##### To
*Workflow annotations* allow you to simplify spring configuration part: *no more needed to register* your listener into ```activitiBeanRegistry```,
just annotate your bean

```java
@ActivitiBean("customTaskListener")
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        ...
    }
}
```

and use bean name in your workflow definition (such as ```${customWfListener}```): [```ActivitiBeanPostProcessor```](src/main/java/it/cosenonjaviste/alfresco/annotations/processors/runtime/ActivitiBeanPostProcessor.java)
post processor will register your bean for you gracefully.


There are some **shortcutting stereotypes** extending ```@ActivitiBean```, enabling coherence check on class hierarchy and listener events.

We recommend to use these annotations:

##### @TaskListenerBean

Enables check on interfaces and throw a compilation error if the annotated class error does not implement ```TaskListener``` interface

```java
@TaskListenerBean("customTaskListener")
public class MyTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        ...
    }
}
```

##### @OnCreateListenerBean

Enables check on superclasses and throws a compilation error if the annotated class does not extend ```TaskCreateListener``` class.

```java
@OnCreateListenerBean("customCreateTaskListener")
public class MyTaskCreateListener extends TaskCreateListener {
    @Override
    public void notify(DelegateTask task) {
        super.notify(task);

        ...
    }
}
```

Such listeners are notified when a **task is created**.

##### @OnCompleteTaskListener

Enables check on superclasses and throws a compilation error if the annotated class does not extend ```TaskCompleteListener``` class.

```java
@OnCompleteTaskListener("customCompleteTaskListener")
public class MyTaskCompleteListener extends TaskCompleteListener {
    @Override
    public void notify(DelegateTask task) {
        super.notify(task);

        ...
    }
}
```

Such listeners are notified when a **task is completed**.


