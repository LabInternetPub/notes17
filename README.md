# Spring Web MVC

## HTTP(S) protocol
This is the protocol used in all web calls. One thing you need to keep in mind is that **HTTP does NOT have state** meaning 
that two different http requests are completely independent and have nothing to do each other.

In order to keep an *illusion* of state (like keeping a session for an user) there are a couple of tricks. 
* Write path parameters to send the session id
* Keep the session id in cookies   

## GET method
* Static pages
* Dynamic pages: JSP vs Thymeleaf (and others)
* Request variables
* Path variables (generally preferred)

### GET examples
* http://localhost:8080/welcome.html
* http://localhost:8080/welcome_dynamic
* http://localhost:8080//welcome_requestParam?message=Hello%20to%20everybody
* http://localhost:8080//welcome_pathVariable/Hello%20to%20everybody
* http://localhost:8080/users
* http://localhost:8080/users/roure 

## POST method
* Forms best use POST method instead of GET. POST sends parameters in the http body request while GET sends them concatenated
in the request query string and the user can easily see them 
* If *"action"* is not stated in a *form* it sends the request to the same link used to call the form
* After processing the form the *servlet (controller)* should redirect to another page (for example showing the effects of
the action) in order to avoid unwillingly re-sending the form

### Redirected attributes
Once the form is processed we should redirect to another page and we may want to send attributes to the redirected page.
There are three kind of attributes:
* Request parameter
* Path variable
* Flash attributes: sent through the web context, in fact, these attributes never leave the server! The servlet responding 
the request access this kind of attributes through the Model object 

### POST examples
* **Post form, create a new user (with no error control)**: http://localhost:8080/createUserNoErrorControl
* **Redirect attributes example** http://localhost:8080/createUserNoErrorControl/redirectAttributesExample

### Forward vs Redirect
Extracted from: http://www.javapractices.com/topic/TopicAction.do?Id=181

####Forward 
* a forward is performed internally by the servlet
* the browser is completely unaware that it has taken place, so its original URL remains intact
* any browser reload of the resulting page will simple repeat the original request, with the original URL

#### Redirect
* a redirect is a two step process, where the web application instructs the browser to fetch a second URL, which differs from the original
* a browser reload of the second URL will not repeat the original request, but will rather fetch the second URL
* redirect is marginally slower than a forward, since it requires two browser requests, not one
* objects placed in the original request scope are not available to the second request
