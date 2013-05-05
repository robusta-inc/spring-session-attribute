<h1>@SessionAttribute for Spring-Mvc</h1>
1. An annotation @SessionAttribute for Spring Mvc handler method parameters
2. An extensive Spring Mvc WebArgumentResolver implementation



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
<ol>

<h6>Note:</h6>
I would'nt advice using http session in building spring mvc based web and REST applications, 
but if you have to, do it annotation driven and test driven.

<h6>License: Apache 2.0</h6>
