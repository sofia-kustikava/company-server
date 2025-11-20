package com.example.companyserver.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super("Could not find user " + message);
    }
}
