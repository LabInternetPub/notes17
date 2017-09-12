# Accessing the data base with JdbcTemplate

You can find the official documentation at: https://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html

### Boilerplate code
Working with plain JDBC drivers is hard in the sense that it requires a lot of boilerplate code that is prone to lots of errors.
The JdbcTemplate writes all this boilerplate code for you saving a lot of time in writing large chunks of code with no 
value in it and in debugging this valueless code.

### Meaningless invasive exception
Another drawback of working with plain JDBC is that it provides a single exception (in fact there are four but one is the most common)
that is mandatory to catch. This is terrible because of at least two facts:
* Your code needs to deep into de exception message in order to figure out what is the reason that provoked the exception. 
As you can see this tightly couples your code with JDBC
* You need to clutter your code with lots and lots of try/catch blocks that makes the code unreadable and also in those try/catch
blocks you usually can't do anything useful. What can you do if the database doesn't connect?!!!

## JdbcTemplate
It writes the boilerplate code for you. Basically it implements a Template design pattern: 
https://www.javacodegeeks.com/2015/09/template-design-pattern.html

In the following table (taken from the official documentation) you can see what JdbcTemplate can do for you

| Action | Spring | You |
| ------ |:------:|:---:|
| Define connection parameters. |   | X |
| Open the connection.          | X |   |
| Specify the SQL statement.    |   | X |
| Declare parameters and provide parameter values. |   | X |
| Prepare and execute the statement.| X |   |
| Set up the loop to iterate through the results (if any). | X |   |
| Do the work for each iteration. |   | X |
| Process any exception. | X |   |
| Handle transactions.   | X |   |
| Close the connection, statement and resultset. | X |   |
 
In this repository you can find an example of using the JdbcTemplate

## Security: sql injection
Avoid building sql statements by concatenating strings. This is very dangerous because users can *inject* sql code in the 
filed text they provide. For example the query
```java
txtSQL = "SELECT * FROM Users WHERE UserId = " + txtUserId;
```
can be easily fooled by injecting the following string:
 
```sql
105 OR 1 = 1
```

The final sql statement would look like:
```sql
SELECT * FROM Users WHERE UserId = 105 OR 1=1;
```
which would return all users from the table since 1=1 will always evaluate to true.

Instead use sql parameters that will protect your code from injections as the sql engine will check whether each parameter is correct
for its column and will only allow one single value.
```java
   public UserLab findByUsername(String userName) {
        String FIND_BY_USERNAME = "Select * from user_lab where username = ?";
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, mapperEager, userName);
    }
```

## Developers tools
It is very convenient not to work with the real database in production since you do not want to have test data in it and also because you want to 
avoid the time overhead of having to connect to a remote database every time you test your code. For this reason Spring Boot provides a 
memory database (H2). You only
need to import the **devtools**, the **starter-web** and the **h2** libraries in your *pom.xml*. A H2 datasorce is going to be created
for you and initialized with the files in the resources package called *schema.sql* and *data.sql* if present. 

You also can see the database console typing http://localhost:8080/h2-console/ in your favorite browser. There you'll need to specify 
**jdbc:h2:mem:testdb** in the JDBC URL field. 

## Using an Oracle database
Of course you'll want to use a *real* database. For using an Oracle database you'll need to download the drivers first (unfortunately
they are not in central maven). Once you've downloaded the drivers you need to install them using the following maven statement (you can use
the IntelliJ maven console)
```
mvn install:install-file -Dfile=C:\Users\roure\.IntelliJIdea2016.2\config\jdbc-drivers\ojdbc6-12.1.0.2.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=12.1.0.2 -Dpackaging=jar
```

Another, and easier, way of importing the Oracle drivers is letting IntelliJ doing so for you. You only need to connect to an Oracle 
database. For example try using the database tool: View --> Tool Windows --> database

and then import the library in your *pom.xml* file:
```xml
<dependency>
 <groupId>com.oracle</groupId>
 <artifactId>ojdbc6</artifactId>
 <version>12.1.0.2</version>
</dependency>
```
Still you need to configure the datasource for your Oracle database. The easiest way is using the *application.properties* file 
adding the connection information:
```
spring.datasource.url= jdbc:oracle:thin:@//kali.tecnocampus.cat:1521/sapiens
spring.datasource.username=labinternet
spring.datasource.password=labinternet
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

## Profiles and properties
probably you'd like to have different profiles to configure differently depending on what environment you are working on 
(for example developer and production). To do so, Spring offers the profiles and the properties.

In our example we have defined a profile named **dbmemory** that uses the embedded H2 database. When this profile is set 
active (see *application.properties*) file the *application-dbmemories.properties* is loaded. There the **spring.datasource.platform=h2**
is defined in order that when the application is initializing and populating the database it reads the *data-h2.sql* and 
*schema-h2.sql* files instead of the default ones.

When the **dbmemory** profile is not active the Spring initialization process takes the database configuration from the 
properties defined in the *application.properties* file and creates the bean defined in *configuration/OracleConfiguration*.
Note that this bean is created only when the **dbmemory** profile is not active and takes the configuration values from the 
**property** defined in the application properties as specified in the notations on the OracleConfiguration class:
```java
@Profile("!dbmemory")
@Configuration
@ConfigurationProperties("oracle")
```
