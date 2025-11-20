package com.example.companyserver.exceptions;

public class HaveSubscriptionException extends RuntimeException{
    public HaveSubscriptionException(String message) {
        super("You already have a subscription: " + message);
    }
}
