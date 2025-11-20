package com.example.companyserver.exceptions;

public class MaximumCompaniesException extends RuntimeException{
    public MaximumCompaniesException() {
        super("You can't add another company.");
    }
}
