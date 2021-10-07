package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.CompanyNotFoundException;
import com.example.companyserver.exceptions.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CompanyExceptionHandler {

    @ExceptionHandler({CompanyNotFoundException.class})
    public ResponseEntity<Object> companyNotFound(CompanyNotFoundException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
