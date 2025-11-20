package com.example.companyserver.controller.advice;

import com.example.companyserver.exceptions.CompanyNotFoundException;
import com.example.companyserver.exceptions.ExceptionResponse;
import com.example.companyserver.exceptions.NotFoundException;
import com.example.companyserver.exceptions.ServerErrorException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CompanyExceptionHandler {

    @ExceptionHandler({CompanyNotFoundException.class})
    public ResponseEntity<ExceptionResponse> companyNotFound(CompanyNotFoundException e) {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ExceptionResponse> handleFeignStatusException() {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message("Feign error")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException() {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message("This company doesn't exist in our database")
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> dataException() {
        return new ResponseEntity<>(ExceptionResponse
                .builder()
                .message("You try to add existing symbol. Choose another one.")
                .build(), HttpStatus.NOT_FOUND);
    }
}
