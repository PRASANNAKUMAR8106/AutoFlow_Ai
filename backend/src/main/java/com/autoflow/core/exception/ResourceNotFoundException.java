package com.autoflow.core.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AutoFlowException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    public ResourceNotFoundException(String resource, String id) {
        super(String.format("%s with id %s not found", resource, id), HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }
}
