package com.microservice.finnhub.controller.advice;

import com.microservice.finnhub.exception.ExceptionResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class CompanyExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
