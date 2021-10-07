package com.example.companyserver.exceptions;

public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String message) {
        super("There is no companies with this symbol " + message);
    }
}
