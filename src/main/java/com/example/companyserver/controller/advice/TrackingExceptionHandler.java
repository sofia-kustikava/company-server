package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrackingExceptionHandler {

    @ExceptionHandler({MaximumCompaniesException.class})
    public ResponseEntity<Object> maximumCompanies(MaximumCompaniesException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotTrackingException.class})
    public ResponseEntity<Object> notTracking(NotTrackingException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoAccessTrackingException.class})
    public ResponseEntity<Object> noAccessTracking(NoAccessTrackingException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

}
