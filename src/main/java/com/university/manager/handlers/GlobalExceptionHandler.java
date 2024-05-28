package com.university.manager.handlers;


import com.university.manager.dtos.ErrorRepsonse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, BadCredentialsException.class, RuntimeException.class})
    public ResponseEntity<Object> handleException(Exception e) {
        ErrorRepsonse errorRepsonse = new ErrorRepsonse();
        errorRepsonse.setMessage(e.getMessage());
        return new ResponseEntity<>(errorRepsonse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}