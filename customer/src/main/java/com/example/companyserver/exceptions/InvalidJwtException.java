package com.example.companyserver.exceptions;

public class InvalidJwtException extends RuntimeException{
    public InvalidJwtException() {
        super("Invalid token entered");
    }
}
