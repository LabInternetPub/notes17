# Spring Web MVC

## GET method
* Static pages
* Dynamic pages: JSP vs Thymeleaf (and others)
* Request variables
* Path variables (generally preferred)

### GET examples
* http://localhost:8080/users
* http://localhost:8080/users/roure 

## POST method
* Forms best use POST method instead of GET. POST sends parameters in the http head request while GET sends them concatenated
in the request query string.
* If *"action"* is not stated in a *form* it sends the request to the same link used to call the form
* After processing the form the *servlet (controller)* should redirect to another page (for example showing the effects of
the action) in order to avoid unwillingly re-sending the form

### POST examples
* http://localhost:8080/createUser/noErrorControl

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
