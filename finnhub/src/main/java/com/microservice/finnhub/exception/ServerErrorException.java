package com.microservice.finnhub.exception;

public class ServerErrorException extends Exception{
    public ServerErrorException(String message) {
        super(message);
    }
}
