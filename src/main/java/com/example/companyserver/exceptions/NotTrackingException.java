package com.example.companyserver.exceptions;

public class NotTrackingException extends RuntimeException{
    public NotTrackingException(String message) {
        super("This user don't have an access to tracking this company: " + message);
    }
}
