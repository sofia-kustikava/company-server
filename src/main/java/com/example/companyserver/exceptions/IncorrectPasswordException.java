package com.example.companyserver.exceptions;

import javax.persistence.NonUniqueResultException;

public class IncorrectPasswordException extends NonUniqueResultException {
    public IncorrectPasswordException() {
        super("Password for this user is incorrect. Please, try to enter the password again ");
    }

}
