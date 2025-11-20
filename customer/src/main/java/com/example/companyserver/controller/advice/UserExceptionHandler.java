package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ExceptionResponse> userNotFound(UserNotFoundException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserNotExistException.class})
    public ResponseEntity<ExceptionResponse> userNotExist(UserNotExistException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectPasswordException.class})
    public ResponseEntity<ExceptionResponse> incorrectPassword(IncorrectPasswordException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidUserParameterException.class})
    public ResponseEntity<ExceptionResponse> incorrectUserParameter(InvalidUserParameterException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserAlreadyExistException.class})
    public ResponseEntity<ExceptionResponse> userAlreadyExist(UserAlreadyExistException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserIsBannedException.class})
    public ResponseEntity<ExceptionResponse> userIsBanned(UserIsBannedException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserIsUnbannedException.class})
    public ResponseEntity<ExceptionResponse> userIsBanned(UserIsUnbannedException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidJwtException.class})
    public ResponseEntity<ExceptionResponse> invalidToken(InvalidJwtException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
