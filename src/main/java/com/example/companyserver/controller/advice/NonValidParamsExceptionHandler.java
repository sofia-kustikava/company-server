package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NonValidParamsExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleGlobalExceptions(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message("Incorrect parameters entered")
                .build(), HttpStatus.BAD_REQUEST);
    }
}
