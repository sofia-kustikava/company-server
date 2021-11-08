package com.example.companyserver.exceptions;

public class NoSubscriptionException extends RuntimeException{
    public NoSubscriptionException(String message) {
        super("There is no subscription for this user: " + message);
    }
}
