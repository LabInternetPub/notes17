package cat.tecnocampus.aop;

import cat.tecnocampus.domain.NoteLab;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roure on 17/10/2016.
 */
@Aspect
@Component
public class LoggerAdvice {
	
    private static final Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);


    //A pointcut that matches one single method
    @Pointcut("execution(* cat.tecnocampus.useCases.UserUseCases.getUsers(..))")
    public void pointcutListUsers() {}

    @Before("pointcutListUsers()")
    public void beforeListUsers() {
        logger.info("Going to list all users");
    }

    @After("pointcutListUsers()")
    public void afterListUsers() {
        logger.info("Already listed all users");
    }


    //A pointcut that matches all methods having the word "Notes" in any position of methods' name
    @Pointcut("execution(* cat.tecnocampus.useCases.UserUseCases.*Notes*(..))")
    public void pointcutNotes() {}

    @Before("pointcutNotes()")
    public void beforeDealingNotes() {
        logger.info("Going to deal with notes");
    }

    //Around pointcut. Note that this method must return what the proxied method is supposed to return
    @Around("pointcutNotes()")
    public List<NoteLab> dealRequestParam(ProceedingJoinPoint jp) {

        try {
            logger.info("Before showing notes");
            //note that showUserRequestParameter is proxied and it must return a string
            // representing the thymeleaf file name
            List<NoteLab> res = (List<NoteLab>) jp.proceed();
            logger.info("After showing user notes");
            return res;
        } catch (Throwable throwable) {
            logger.info("Showing notes: Something went wrong");
            throwable.printStackTrace();
            return new ArrayList<NoteLab>();
        }
    }


    //Getting the parameters of the proxied method
    @Pointcut("execution(* cat.tecnocampus.useCases.UserUseCases.getUser(..)) && args(userName)")
    public void showUserPointcut(String userName) {}

    @Before("showUserPointcut(userName)")
    public void showUserAdvice(String userName) {
        logger.info("Going to show user: " + userName);
    }
}
