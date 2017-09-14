# Aspect-Oriented Programming
  
  ## Introduction
  The following paragraph is mainly taken from: https://en.wikipedia.org/wiki/Aspect-oriented_programming
  Aspect-oriented programming (AOP) is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. It does so by adding additional behavior to existing code (an advice) without modifying the code itself, instead separately specifying which code is modified via a "pointcut" specification, such as "log all function calls when the function's name begins with 'set'". This allows behaviors that are not central to the business logic (such as logging) to be added to a program without cluttering the code core to the functionality. AOP forms a basis for aspect-oriented software development.
  AOP includes programming methods and tools that support the modularization of concerns at the level of the source code, while "aspect-oriented software development" refers to a whole engineering discipline.
  As example we can name the following:
  * Logging
  * Security
  * Transactions
  
  ## Terminology
  Here we’ll define the terminology used in AOP
  
  Advice: defines the what and the when of an aspect. So it describes the job an aspect will perform and when the advice is performing the job. 
  In Spring there are five kinds of advice:
  * Before
  * After
  * After-returning
  * After-throwing
  * Around
  
  **Join points**: Opportunities where an advice could be applied. A join point is a point in the execution of the application where an aspect can be plugged in (a method call, a thrown exception, a field being modified).
  
  **Ponitcuts**: define the where an advice can be applied. A pointcut definition matches one or more join points at which advice should be woven. These pointcuts are defined by stating classes or methods names, or even regular expressions to define name patterns.
  
  **Aspect**: is the merger of advice and pointcuts. Taken together, advice and pointcuts define everything there is to know about an aspect (what, when and where)
  
  **Introduction**: allows you to add new methods or attributes to existing classe. It is a good way of extending classes for which you don’t have the code
  
  **Weaving**: is the process of applying aspects to a target object to create a new proxied object. Aspects could be applied at compile time, class load time or runtime
  
  ## Spring AOP model
  It’s not as rich as the ones defined in the AspectJ project: http://www.eclipse.org/aspectj/
  
  Spring advice is written in Java: other implementations have their own specific language. 
  Spring advices objects at runtime by wrapping them with a proxy class. The proxy intercepts the advised method call, makes the additional work (the one in the avise)  and calls the actual method. Spring creates the proxies during runtime at the moment they are needed. In this way t does not need a special compiler to weave aspects.
  Spring only supports method joint points. This is a consequence of using dynamic proxies
  
  Spring documentation: https://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html
  
  ## Writing pointcuts
  We need to define the methods that should be advised. It is rather complicated at first. Spring uses AspectJ designators to define the pointcuts:
  
  * **args()** : limits joinpoint matches to those methods whose arguments are instances of the given types
  * **@args()** : limits joinpoint matches to those methods whose arguments are annotated with the given annotation types
  * **execution()** : matches joinpoints that are methods executions
  * **this()** : limits joinpoint matches to those where the bean reference of the AOP proxy is of a given type
  * **target()** : limits joinpoint matches to those where the target object is of a given tiype
  * **@target()** : 
  * **within()** : limits matching to join points within certain types
  * **@within()** :
  * **@annotation** :  limits joinpoint matches to those where the subject of the joinpoint has the given annotation
  
  Let’s say we want to advise the method perform() defined in class concert.Performance
  
  ```
  execution(* concert.Performance.perform(..))
  ```
  
  * execution: triggers on method’s execution 
  * \* returning any type
  * .. taking any arguments
  
  ```
  execution(* concert.Performance.perform(..)) && within(concert.*)
  ```
  
  * && combinator operator (and). You can use || and ! logic operators
  * within(concert.*) : when the method is called from within any class in the “concert” package
  
  Spring defines a new designator (not in AspectJ) “bean()” that allows to designate beans by their ID in a pointcut definition
  
  ```
  execution(* concert.Performance.perform(..)) && bean(‘pepe’)
  ```
  
  In this case the advice is going to be executed if perform is called from the bean ‘pepe’
 
 ## Creating annotated aspects
  Annotate a class for it to be an aspect
  @Aspect 
  
  Annotate methods to be weaved and define pointcuts
  @Before(“<pointcut definition”)
  @After(“<pointcut definition”)
  @AfterReturning(“<pointcut definition”)
  @AfterThrowing(“<pointcut definition”)
  @Around(“<pointcut definition”)
  
  Pointcuts can be defined separately and used in the previous annotations. The name of the pointcut definition is the name of the empty method that goes just afterwards
  ```java
  @Pointcut(“<pointcut definition”)
  Public void pointcutDefName() {}
  ```
  
  Whe need to create the Aspect class as a bean or component for the Spring framework to use it when necessary.
  
  A very simple example: https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-aop
  
  ### @Around advice
  It’s the most powerful advice type. It allows you to write logic that completely wraps the advised method. 
  
  The method that implements the around advice has a parameter with an object that represents the advised method. Using this method de advice can invoke the advised method.  
  
  An interesting point is that you can decide not to call the advised method (why should do you want not to?) and also that you can invoke it repeatedly. For example to implement a retry logic.
  
 ```java
  @Around(“<pointcut”>)
  public void adviceMethod(ProceedingJoinPoint jp) {
  	do whatever;
  	jp.proceed();
  	do whatever;
  }
  ```
  ## Parameters
  We can use the parameters of the proxied method and use them in the advice`
  
  ```java
  @Pointcut("execution(* cat.tecnocampus.webControllers.UserUseCaseController.showUser(..)) && args(name,..)")
  public void showUserPointcut(String name) {}
  
  @Before("showUserPointcut(name)")
  public void showUserAdvice(String name) {
     logger.info("Going to show user: " + name);
  }
```