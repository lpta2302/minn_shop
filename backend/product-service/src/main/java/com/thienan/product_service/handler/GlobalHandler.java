package com.thienan.product_service.handler;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.thienan.product_service.handler.exceptions.common.EntityNotFoundException;
import com.thienan.product_service.handler.exceptions.weight_type.InvalidWeightRangeException;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(exception=EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(BAD_REQUEST.value())
                .message(exception.getMessage())
                .build()
            );
    }

    @ExceptionHandler(exception=InvalidWeightRangeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidWeightRangeException(InvalidWeightRangeException exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(BAD_REQUEST.value())
                .message(exception.getMessage())
                .build()
            );
    }

}
