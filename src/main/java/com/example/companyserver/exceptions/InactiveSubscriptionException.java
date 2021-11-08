package com.example.companyserver.exceptions;

public class InactiveSubscriptionException extends RuntimeException{
    public InactiveSubscriptionException(String message) {
        super("Subscription is inactive for this user: " + message);
    }
}
