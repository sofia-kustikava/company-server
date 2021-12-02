package com.microservice.finnhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String message) {
        super("There is no companies with this symbol " + message);
    }
}
