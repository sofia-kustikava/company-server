package com.microservice.finnhub.exception;

public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String message) {
        super("There is no companies with this symbol " + message);
    }
}
