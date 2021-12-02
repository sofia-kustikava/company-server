package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrackingExceptionHandler {

    @ExceptionHandler({MaximumCompaniesException.class})
    public ResponseEntity<ExceptionResponse> maximumCompanies(MaximumCompaniesException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotTrackingException.class})
    public ResponseEntity<ExceptionResponse> notTracking(NotTrackingException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoAccessTrackingException.class})
    public ResponseEntity<ExceptionResponse> noAccessTracking(NoAccessTrackingException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> conflict() {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message("You can't add the same company")
                .build(), HttpStatus.BAD_REQUEST);
    }

}
