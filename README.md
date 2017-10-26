# Spring REST

**REST** stands for REST (Representational State Transfer) was introduced and defined in 2000 by Roy Fielding in his doctoral 
dissertation. REST is an architectural style for designing distributed systems. It is not a standard but a set of constraints, 
such as being stateless, having a client/server relationship, and a uniform interface. REST is not strictly related to HTTP, 
but it is most commonly associated with it.

It was born as an alternative to **SOA** (Service Oriented Architecture) that defines a very strict and complicated protocol. Nowadays
REST style is used by most of the applicatinons on Internet to expose their service API   

* https://spring.io/understanding/REST
* https://en.wikipedia.org/wiki/Representational_state_transfer

## Principles of REST

* **Client-Server architecture** Separating the user interface concerns from the data storage concerns improves the portability 
of the user interface across multiple platforms. It also improves scalability by simplifying the server components (see later points).

* **Cacheability** As on the World Wide Web, clients and intermediaries can cache responses. Responses must therefore, implicitly or 
explicitly, define themselves as cacheable or not to prevent clients from reusing stale or inappropriate data in response to further 
requests. Well-managed caching partially or completely eliminates some client–server interactions, further improving scalability and performance.

* **Uniform interface** it simplifies and decouples the architecture, which enables each part to evolve independently. 
The four constraints for this uniform interface are:
    * **Resources** expose easily understood directory structure URIs. A resource corresponds to a URI.
    * **Representations** transfer JSON or XML to represent data objects and attributes.
    * **Messages** use HTTP methods explicitly (for example, GET, POST, PUT, and DELETE), headers and message codes.
    * **Stateless** interactions store no client context on the server between requests. State dependencies limit and restrict 
scalability. The client holds session state.
    * **HATEOAS** Hypermedia as the engine of application state. The server should provide links along with the resources
    so that clients are able to discover all available actions and additional resources they may need.

## Our example (Notes)
Note that we have a new package called *RESTcontrollers* where we can find the *UserRESTController.java*. In this last file
we can see that the class is annotated with *@RestController*. We observe that there are two methods *listUser* and *showUser*
that are annotated with @GetMapping, that work pretty much the same as we so with web controllers. There is one difference though, the
annotation has a new parameter stating the type it produces (JSON: JavaScript Object Notation). See http://json.org/ 

We've got other methods annotated with:
* PostMapping: these methods accept a JSON object representing a new user or note in the body of the request and a new user or note is created
* PutMapping: accepts a PUT request in order to modify a note
* DeleteMapping : accepts a DELETE request in order to delete the given note. In these two last mappings the objects are 
sent to the server in the request body as JSON objects

## Marshaling and unmarshalling JSON objects
When the rest controller receives a JSON object in the request (for example in the request body) it converts it to a java POJO and
viceversa, when it returns a POJO or a collection of POJOS thet are converted to JSON objects. This is done by a filter by means
of a library called Jackson. See the pom.xml file:
```javascript
		<dependency>
			<groupId>com.fasterxml.jackson.datatype</groupId>
			<artifactId>jackson-datatype-jsr310</artifactId>
		</dependency>
```
See classes Note and User that now have annotations corresponding the the Jackson library:
* In Note one date has the following annotation @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") while
the other it doesn't. Note the difference in the produced Json
* In UserLab @JsonIgnore to ignore an attribute
* In UserLab @JsonProperty("notes") to name an attribute

In the following link you can find all the available annotations
https://github.com/FasterXML/jackson-annotations

## REST client
In order to test the API REST we will need a REST client. There are plenty out there. I usually use one called Postman which is a 
plug for Chrome. With REST clients we can send requests and receive responses. These clients allow us to modify all the parameters
of a request such as the head and the body of the request. 
For example: 
* we could POST a new note by requesting a POST action to the link http://localhost:8080/api/users/roure/notes and adding to 
the body the following Json:
```javascript
{
    "title": "A REST note",
    "content": "This is the content of the note"
}
```
* POST a new user by posting an action to the link http://localhost:8080/api/users and adding to the body the following Json:
```javascript
{
    "username": "casagran",
    "name": "Sonia",
    "secondName": "Casagran",
    "email": "casagran@mail.cat",
    "password": "casagran"
}
```
* We could then modify and delete a note by using the PUT and DELETE actions. Notes will be identified by title and their 
creation dates.  
