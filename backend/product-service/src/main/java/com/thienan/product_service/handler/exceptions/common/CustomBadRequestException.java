package com.thienan.product_service.handler.exceptions.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Getter
public class CustomBadRequestException extends ResponseStatusException {
    private final Map<String, String> details;

    public CustomBadRequestException(String message, Map<String, String> details) {
        super(BAD_REQUEST, message);
        this.details = details;
    }
}
