package com.example.companyserver.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(final String message) {
        super("There is an account with that email address: " + message);
    }
}
