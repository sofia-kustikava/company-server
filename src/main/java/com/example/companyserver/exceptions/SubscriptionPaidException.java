package com.example.companyserver.exceptions;

public class SubscriptionPaidException extends RuntimeException{
    public SubscriptionPaidException(String message) {
        super("You don't have an access to pay for the subscription: " + message);
    }
}
