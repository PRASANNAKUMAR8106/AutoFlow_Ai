package com.autoflow.api.exception;

import com.autoflow.api.response.ApiResponse;
import com.autoflow.core.exception.AutoFlowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AutoFlowException.class)
    public ResponseEntity<ApiResponse<Void>> handleAutoFlowException(AutoFlowException ex) {
        log.error("Business exception: {} - {}", ex.getErrorCode(), ex.getMessage());
        return ResponseEntity
                .status(ex.getStatus())
                .body(ApiResponse.error(ex.getMessage(),
                    ApiResponse.ApiError.builder()
                            .code(ex.getErrorCode())
                            .build()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        log.error("Validation failed: {}", errors);
        return ResponseEntity.badRequest().body(ApiResponse.error(
                "Validation failed",
                ApiResponse.ApiError.builder()
                        .code("VALIDATION_ERROR")
                        .validationErrors(errors)
                        .build()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMalformedJson(HttpMessageNotReadableException ex) {
        log.error("Malformed JSON request: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ApiResponse.error(
                "Malformed JSON request",
                ApiResponse.ApiError.builder()
                        .code("MALFORMED_JSON")
                        .build()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        log.error("Unexpected error occurred: ", ex);
        return ResponseEntity.internalServerError().body(ApiResponse.error(
                "An unexpected error occurred on the server",
                ApiResponse.ApiError.builder()
                        .code("INTERNAL_SERVER_ERROR")
                        .detail(ex.getMessage())
                        .build()));
    }
}
