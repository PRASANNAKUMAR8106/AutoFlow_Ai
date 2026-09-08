package com.autoflow.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AutoFlowException extends RuntimeException {
    private final HttpStatus status;
    private final String errorCode;

    public AutoFlowException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
