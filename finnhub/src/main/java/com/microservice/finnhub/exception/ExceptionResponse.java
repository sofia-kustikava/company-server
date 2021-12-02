package com.microservice.finnhub.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ExceptionResponse {
    private final String message;
}
