package am.trainings.palmetto.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception e) throws Exception {
        // Let Spring handle its own MVC exceptions (404, 405, 406...) with the proper status
        if (e instanceof org.springframework.web.ErrorResponse) {
            throw e;
        }
        log.error("Unhandled exception while processing request", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
