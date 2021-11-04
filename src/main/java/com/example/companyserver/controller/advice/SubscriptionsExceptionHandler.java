package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.ExceptionResponse;
import com.example.companyserver.exceptions.HaveSubscriptionException;
import com.example.companyserver.exceptions.SubscriptionNotExistException;
import com.example.companyserver.exceptions.SubscriptionPaidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SubscriptionsExceptionHandler {

    @ExceptionHandler({SubscriptionNotExistException.class})
    public ResponseEntity<Object> subscriptionNotExist(SubscriptionNotExistException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HaveSubscriptionException.class})
    public ResponseEntity<Object> haveSubscription(HaveSubscriptionException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SubscriptionPaidException.class})
    public ResponseEntity<Object> subscriptionPaid(SubscriptionPaidException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
