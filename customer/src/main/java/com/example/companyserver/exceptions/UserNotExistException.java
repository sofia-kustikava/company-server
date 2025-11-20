package com.example.companyserver.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(String message) {
        super("This user doesn't exist " + message);
    }
}
