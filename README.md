# Alfresco Annotations
Spring stereotype annotations for simplifying development with Alfresco

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

May works on other micro-versions. Please edit this file and pull-request if works on other versions.

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

enable *annotation discovery by package* in <tt>module-context.xml</tt> (or any you prefer):

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

## Annotations

Each annotation's javadoc helps you to remember which class you should or must extend, what annotation attributes you must provide and what are default values

* [@ActionExecuter](#actionexecuter)
* [@Behaviour](#behaviour)
* [@JsExtension](#jsextension)
* [@ModuleComponent](#modulecomponent)
* [@WebScript](#webscript)
 * [@WebScriptDescriptor](#webscriptdescriptor)
 
### @ActionExecuter
Creating a new **Java Action** is pretty easy: just annotate your bean with ```@ActionExecuter```, name it and don't forget to extends ```ActionExecuterAbstractBase``` as annotation's javadoc says! (But forget bean parent name... this annotation will take care of it)

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

### @Behaviour
Stop remembering messy **Behaviour** policy registration: annotate your bean (implementing one or more ```org.alfresco.repo.policy.ClassPolicy``` children) such as

```java
@Behaviour(value = "myBehaviour", type = "cm:content")
public class MyBehavior implements NodeServicePolicies.OnUpdatePropertiesPolicy {

  @Override
  public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {
    // do stuff on properties update for cm:content
  }
}
```
and thre you go, ready to behave!

Since Alfresco 5, you can choose to use standard annotations ```org.alfresco.repo.policy.annotation.Behaviour``` and ```org.alfresco.repo.policy.annotation.BehaviourBean```.
### @JsExtension
JavaScript API is easy and succinct in Alfresco, always powerful but sometimes not enought! To plug a new feature from Java is easy:

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
```@JsExtension``` will register bean name as **Extension** name.

### @ModuleComponent
**Module Components** are special beans to be aimed at single execution for a specific AMP version, useful for initialization/update purpose.

Module Components require some configuration you can achieve through annotation parameters:

```java
@ModuleComponent(moduleId = "my-module-id", name = "myComponent", sinceVersion = "1.0.0")
public class HelloComponent extends AbstractModuleComponent {

  @Override
  protected void executeInternal() throws Throwable {
    // execute on startup
  }
}
```

Take care of ```moduleId```: it *must correspond* to your **module ID**, defined by *module.id* property in ```module.properties``` file.

### @WebScript
WebScript are the most used feature in Alfresco, allowing access to repository feature in a RESTful way. Remembering Java-backed WebScript controller naming convention is pretty boring. I'd like to set a bean name and an HTTP method to define a new WebScript such as:

```java
@WebScript(value = "myWebscript", method = HttpMethod.POST)
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
The most annoying thing to deal with WebScript is to write **xml descriptor**. If you registered ```<generatedSourcesDirectory>``` in ```maven-compiler-plugin```, a class annotated like this:

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

will generate ```myWebScript.get.desc.xml``` with content in your source folder:

```xml
<webscript>
  <shortname>hello-again</shortname>
  <description></description>
  <url>/v1/my-webscript</url>
  <authentication>user</authentication>
  <format default="json">any</format>
</webscript>
```

It's up to you to complete MVC with ```myWebScript.get.json.ftl``` file.
