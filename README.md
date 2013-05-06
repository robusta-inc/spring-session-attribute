<h1>@SessionAttribute for Spring-Mvc</h1>
1. An annotation @SessionAttribute for Spring Mvc handler method parameters
2. An extensible Spring Mvc HandlerMethodArgumentResolver implementation



<div>
  Enables this
  
<pre>
  public String handleSomethingUsingSessionAttribute(
    @RequestParam String param1, 
    @SessionAttribue AuthenticationToken token) {
    
    // controller logic
  }
</pre>
</div>

<h5>Options:</h5>
<ol> 
  <li>Specifying an attribute as Optional/Mandatory</li>
  <li>Specifying to create a new instance of parameter if not found in session</li>
  <li>Always create a new instance in session</li>
</ol>

<h5>Continuous Integration</h5>
On travis - https://travis-ci.org/robusta-inc/spring-session-attribute

<h5>Maven artifacts</h5>
```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>annotated-session-attribute</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
```
available from https://repository-robusta.forge.cloudbees.com/snapshot

<h5>Sample App</h5>
https://github.com/robusta-inc/trail-blazer-webapp

deployed @ http://trail-blazer-app.robusta.cloudbees.net/

<h6>Note:</h6>
I would'nt advice using http session in building spring mvc based web and REST applications, 
but if you have to, do it annotation driven and test driven.

<p>License: Apache 2.0</p>
