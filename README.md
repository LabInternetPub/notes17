# Spring MVC Security

## Documentation
https://docs.spring.io/spring-security/site/docs/current/reference/html/index.html

## Important concepts:
* **Principal**: The user or system that performs an action
* **Authentication**: Establishing that principal's credentials are correct
* **Authorization**: Establishing whether a principal is allowed to perform an action
* **Secured item**: The resource that is being secured 

We need to import the *spring-boot-starter-security* in order to secure our web application. Once we have it imported we aren't 
able to perform any action without being logged in.

## The example
There are different **data stores** to save users and their passwords as well as their authorizations. For example we can use
a memory store (only for development), a jdbc store or a LDAP. In this example we are going to see the first two. For that we'll have 
two different profiles. Namely, *security_memory* and *security_jdbc* and we'll have different *.sql files in order to 
populate the database. Actually the database schema is the same in both cases and it only changes the password of the inserted users. The ones
used with the *security_jdbc* profile stores the passwords coded (it's more secure :-)

## Configuration
We need to configure several things. We'll dot that with a class extending the *WebSecurityConfigurerAdapter*, actually in our example
we have a base class, *BaseSecurityConfig*, that extends WebSecurityConfigurerAdapter and others extending the former and
associated with different profiles (i.e. security_memory and security_jdbc)

### BaseSecurityConfig
* AuthorizedRequests: through *AntMatchers* and *MvcMatchers* we can specify what kind of access it is allowed for each link pattern:
    * /static and /createUser permitAll -> meaning everybody can access these links
    * /users hasRole("USER") -> meaning that all users (having the role USER) can access the link. Note that a non registered user
    cannot list the users
    * /users/{username} and /users/{username}/createNote can only be reached by the *username* user. See the code for two ways
    of doing the same thing
    * /users/{username}/createNote can be reached only if the *username* user was authenticated during the current session (*fullyAuthenticated*)
    * note that the matchers are evaluated in the order that are given so it is advisable (I'd say mandatory) to order the 
    matchers from the most specific to the most general
* FormLogin(): it specifies that a form must be returned when there is a not authenticated request. The form can be specified
otherwise a default form is sent.
* Logout: you can specify the page to go after the user has logged out
* Remember me: see below
* Cross Site Request Forgery (CSRF): https://en.wikipedia.org/wiki/Cross-site_request_forgery . In order to avoid this kind of 
attack a token is added to the request (via forms or via cookies and javascript)
* Basic Http security: The login and password can be sent along with the request. This is used when working with REST services and clients.

### InMemorySecurityConfig
Stores the user information in memory. It is used only for development, NEVER use it for production. Basically it adds a couple of 
users for testing purposes. 

### JdbcSecurityConfig
It uses a database to store the user's information. It basically states:
* The data store to be used. Sets the datasource bean to use
* The password encoder in order to store safely the password in the database. We are using a Bcrypt encoder that uses a 
random salt. It is considered to be quite secure. Information about salted password hashing: https://crackstation.net/hashing-security.htm
* The queries to the database to be used when querying the database for the users and their roles (see the database schema). Sprig 
provides with default queries. 

Note that when creating and registering a user we needed to modify the UserWebPost when the user is created (the POST createUser) 
* The user's password must be encrypted since it is received in plain text as written by the user
* The user must also be added to the authorities table using the *AuthoritiesDAO*  . At least it should have the USER role

### Remember me (if you want to)
It is used for the user not having to log in every time it uses a computer. The *logged in status* is kept for a while (i.e a couple of weeks)
and the user is required to enter his login/password when he is performing a compromised operation. In other words
the logged in status survives the session. (Have you noticed that in the Github web page?)

As you know the HTTP protocol has no state and it has to be simulated by means of cookies. Observe the cookies sent by the
server (it is done automatically by the Spring framework) when we make a first and successive calls, when we log in or 
when we log in withe the remember me. 
(for example in 
the *Chrome developer tools* --> Application (on the top menu) --> Cookies (on the left))  

* Try requesting createNote from a browser with a different session (i.e use safari completely closing the app between requests)
