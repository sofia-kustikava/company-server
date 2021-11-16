package com.example.companyserver.exceptions;

public class ServerErrorException extends Exception{
    public ServerErrorException(String message) {
        super(message);
    }
}
