package com.abc.telecom.billing.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // Convenience constructor for existing call sites using int status codes
    public ApiException(String message, int statusCode) {
        this(message, HttpStatus.resolve(statusCode) != null ? HttpStatus.resolve(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getStatusCode() {
        return status.value();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}