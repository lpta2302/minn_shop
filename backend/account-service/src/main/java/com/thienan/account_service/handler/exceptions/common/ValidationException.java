package com.thienan.account_service.handler.exceptions.common;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
