package se.ductus.tempservice.spring.presentation;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHttpExceptionHandler {

    // Not needed, the framework takes care of mapping 400 Bad request responses
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleResourceNotFound(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
