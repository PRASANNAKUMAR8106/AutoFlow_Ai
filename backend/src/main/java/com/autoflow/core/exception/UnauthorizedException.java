package com.autoflow.core.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AutoFlowException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");
    }
}
