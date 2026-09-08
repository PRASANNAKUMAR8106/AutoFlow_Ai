package com.autoflow.core.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AutoFlowException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "BAD_REQUEST");
    }
}
