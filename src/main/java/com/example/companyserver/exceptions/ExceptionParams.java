package com.example.companyserver.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class ExceptionParams {
    private final String message;
    private final HttpStatus status;
}
