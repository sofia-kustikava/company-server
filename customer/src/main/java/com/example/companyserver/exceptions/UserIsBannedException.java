package com.example.companyserver.exceptions;

public class UserIsBannedException extends RuntimeException{
    public UserIsBannedException(String message) {
        super("This user is already banned: " + message);
    }
}
