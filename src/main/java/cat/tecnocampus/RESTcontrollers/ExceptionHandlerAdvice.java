package cat.tecnocampus.RESTcontrollers;

import cat.tecnocampus.aop.LoggerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<?> handleUsernameDoesNotExist(Model model, HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        return handleError(request, ex, url.substring(url.lastIndexOf("/") + 1) + " not found");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleUsernameAlreadyExists(HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();

        return handleError(request, ex,url.substring(url.lastIndexOf("/") + 1) + " duplicated");
    }

    private ResponseEntity<?> handleError(HttpServletRequest request, Exception ex, String message) {
        String url = request.getRequestURL().toString();
        Error error;

        logger.error("Request: " + request.getRequestURL() + " raised " + ex);

        error = new Error(ex.toString(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    private class Error {
        private String code;
        private String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}

