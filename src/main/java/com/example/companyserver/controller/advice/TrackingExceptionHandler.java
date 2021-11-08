package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrackingExceptionHandler {

    @ExceptionHandler({BannedUserException.class})
    public ResponseEntity<Object> bannedUser(BannedUserException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InactiveSubscriptionException.class})
    public ResponseEntity<Object> inactiveSubscription(InactiveSubscriptionException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MaximumCompaniesException.class})
    public ResponseEntity<Object> maximumCompanies(MaximumCompaniesException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSubscriptionException.class})
    public ResponseEntity<Object> noSubscription(NoSubscriptionException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
