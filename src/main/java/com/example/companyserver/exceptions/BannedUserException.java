package com.example.companyserver.exceptions;

public class BannedUserException extends RuntimeException{
    public BannedUserException(String message) {
        super("This user is banned: " + message);
    }
}
