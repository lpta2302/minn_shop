package com.thienan.auth_service.handler.exceptions.common;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.web.server.ResponseStatusException;

import lombok.Getter;

@Getter
public class CustomBadRequestException extends ResponseStatusException {
    private final Map<String, String> details;

    public CustomBadRequestException(String message, Map<String, String> details) {
        super(BAD_REQUEST, message);
        this.details = details;
    }
}
