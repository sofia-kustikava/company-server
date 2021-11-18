package com.microservice.finnhub.exception;

public class ForbiddenException extends Exception{
    public ForbiddenException(String message) {
        super(message);
    }
}
