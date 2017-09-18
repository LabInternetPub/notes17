# Spring Web MVC: Validating Forms and Exception handling

## Validate Forms

It is important to validate form fields. The most convenient way of doing so is in the client itself by programming few 
javascript validation functions. However, it is also advisable to validate form fields in the server just in case ther
request is not submitted from our own form. 

We can take advantage of the Java Validation API and one of its implementations such as the Hibernate Validator. We just need
to annotate POJOs attributes with one or more of the following: http://docs.oracle.com/javaee/6/tutorial/doc/gircz.html

| Constraint | Description | Example |
| ---------- | ----------- | ------- |
| @AssertFalse | The value of the field or property must be false. | @AssertFalse  <br/> boolean isUnsupported; |
| @AssertTrue | The value of the field or property must be true. | @AssertTrue <br/> boolean isActive; | 
| @DecimalMax <br/> @DecimalMin| The value of the field or property must be a decimal value lower/greater than or equal to the number in the value element. | @DecimalMax("30.00") <br/> BigDecimal discount; |
| @Digits | The value of the field or property must be a number within a specified range. The integer element specifies the maximum integral digits for the number, and the fraction element specifies the maximum fractional digits for the number. | @Digits(integer=6, fraction=2) <br/> BigDecimal price; |
| @Future | The value of the field or property must be a date in the future. | @Future <br/> Date eventDate; |
| @Max @Min | The value of the field or property must be an integer value lower/greater than or equal to the number in the value element. @Max(10) <br/> int quantity; |
| @NotNull | The value of the field or property must not be null. | @NotNull <br/> String username; |
| @Null | The value of the field or property must be null. | @Null <br/> String unusedString; |
| @Past | The value of the field or property must be a date in the past. | @Past <br/> Date birthday; |
| @Pattern | The value of the field or property must match the regular expression defined in the regexp element. | @Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{4}") <br/> String phoneNumber; |
| @Size | The size of the field or property is evaluated and must match the specified boundaries. If the field or property is a String, the size of the string is evaluated. If the field or property is a Collection, the size of the Collection is evaluated. If the field or property is a Map, the size of the Map is evaluated. If the field or property is an array, the size of the array is evaluated. Use one of the optional max or min elements to specify the boundaries. | @Size(min=2, max=240) <br/> String briefMessage; |

You'll see some more examples at UserLab.java where the user attributes are annotated with validation annotations. 
Then the method that receives the UserLab, the one with the POST mapping that receives the UserLab from the form, needs to annotate
the corresponding method parameter with the annotation @Validate. Right after the @Validate annotated parameters (there can be more
than one) an Errors parameters must be declared in order to receive the possible errors produced when building the userLab object.
These errors are also send back to the form for it to use them and tell the user what was wrong.

## Exception Handling

Exceptions will probably be handed off by one of the controllers. If exceptions get to the web controller probably means that either 
the user can do something about it or nobody can't do anything useful. In either case it is good to keep the user informed.
A web controller can handle Exceptions by means of the @HandlerException annotation. These methods can be either inside the web controller or 
or in a new class controller annotated with @ControllerAdvice. In the former case the method will only respond to the exceptions that 
somehow where caused in the controller while in the later annotated methods will respond to any exception signaled in any web controller.
See the example for more detail.
 