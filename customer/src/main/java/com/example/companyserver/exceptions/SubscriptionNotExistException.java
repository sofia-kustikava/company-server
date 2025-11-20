package com.example.companyserver.exceptions;

public class SubscriptionNotExistException extends RuntimeException{
    public SubscriptionNotExistException(String message) {
        super("This subscription doesn't exist with id " + message);
    }
}
