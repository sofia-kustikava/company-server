package com.example.companyserver.exceptions;

public class NoAccessTrackingException extends RuntimeException{
    public NoAccessTrackingException(String message) {
        super("This user don't have an access to this method " + message);
    }
}
