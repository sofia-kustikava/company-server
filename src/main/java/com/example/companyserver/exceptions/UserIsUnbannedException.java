package com.example.companyserver.exceptions;

public class UserIsUnbannedException extends RuntimeException{
    public UserIsUnbannedException(String message) {
        super("This user is already unbanned: " + message);
    }
}
