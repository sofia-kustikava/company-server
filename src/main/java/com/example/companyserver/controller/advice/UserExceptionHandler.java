package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<Object> userNotFound(UserNotFoundException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionParams exceptionParams = new ExceptionParams(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(exceptionParams, badRequest);
    }

    @ExceptionHandler({UserNotExistException.class})
    public ResponseEntity<Object> userNotExist(UserNotExistException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionParams exceptionParams = new ExceptionParams(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(exceptionParams, badRequest);
    }

    @ExceptionHandler({IncorrectPasswordException.class})
    public ResponseEntity<Object> incorrectPassword(IncorrectPasswordException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionParams exceptionParams = new ExceptionParams(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(exceptionParams, badRequest);
    }

    @ExceptionHandler({InvalidUserParameterException.class})
    public ResponseEntity<Object> incorrectUserParameter(InvalidUserParameterException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionParams exceptionParams = new ExceptionParams(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(exceptionParams, badRequest);
    }
}
