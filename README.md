# Alfresco Annotations
Spring stereotype annotations for simplifying development with Alfresco

## Why
Tired of compiling boring Spring xml for defining Alfresco beans? Always forget what's the Java-based WebScripts' naming convention or the Action Executer parent bean name?
Then this project is for you!

## Where is the magic?

There is no magic! This project is simply a collection of **Spring stereotypes annotations**, some **Spring lifecycle processors** to do boilerplate code for you and an **annotation processor** to stop writing webscript xml descriptor!
Just add it to your project dependencies:

```xml
<dependency>
  <groupId>it.cosenonjaviste</groupId>
  <artifactId>alfresco-annotations</artifactId>
  <version>${alfresco.annotation.version}</version>
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
just set *where to write them* on *Maven Compiler Plugin*, such as:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <generatedSourcesDirectory>src/main/amp/config/alfresco/extension/templates/webscripts</generatedSourcesDirectory>
    </configuration>
</plugin>
```

## Annotations

Each annotation's javadoc helps you to remember which class you sould or must extend, what annotation attributes you must provide and what are default values

* [@ActionExecuter](#actionexecuter)
* [@Behaviour](#behaviour)
* [@JsExtension](#jsextension)
* [@ModuleComponent](#modulecomponent)
* [@WebScript](#webscript)
 * [@WebScriptDescriptor](#webscriptdescriptor)
 
### @ActionExecuter
TODO
### @Behaviour
TODO
### @JsExtension
TODO
### @ModuleComponent
TODO
### @WebScript
TODO
#### @WebScriptDescriptor
TODO
