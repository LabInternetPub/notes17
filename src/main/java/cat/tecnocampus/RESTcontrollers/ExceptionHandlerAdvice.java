package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.aop.LoggerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleUsernameDoesNotExist(Model model, HttpServletRequest request, HttpStatus status,
                                                        HttpHeaders headers, WebRequest webRequest, Exception ex) {
        String url = request.getRequestURL().toString();

        String message = url.substring(url.lastIndexOf("/") + 1) + " not found";
        Error error = new Error(status, ex.getLocalizedMessage(), Arrays.asList(message));
        return handleExceptionInternal(ex, error, headers, status, webRequest);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleUsernameAlreadyExists(HttpServletRequest request, WebRequest webRequest,
                                                         HttpStatus status, HttpHeaders headers, Exception ex) {
        String url = request.getRequestURL().toString();

        String message = url.substring(url.lastIndexOf("/") + 1) + " duplicated";
        Error error = new Error(status, ex.getLocalizedMessage(), Arrays.asList(message));
        return handleExceptionInternal(ex, error, headers, status, webRequest);
    }

    private ResponseEntity<?> handleError(HttpServletRequest request, Exception ex, String message) {
        String url = request.getRequestURL().toString();
        Error error;

        logger.error("Request: " + request.getRequestURL() + " raised " + ex);

        error = new Error(HttpStatus.BAD_REQUEST, message, Arrays.asList(message));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        Error error =
                new Error(status, ex.getLocalizedMessage(), errors);
        return handleExceptionInternal(ex, error, headers, status, request);
    }


    private class Error {
        private HttpStatus status;
        private String message;
        private List<String> errors;

        public Error(HttpStatus status, String message, List<String> errors) {
            this.status = status;
            this.message = message;
            this.errors = errors;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public List<String> getErrors() {
            return errors;
        }
    }
}

