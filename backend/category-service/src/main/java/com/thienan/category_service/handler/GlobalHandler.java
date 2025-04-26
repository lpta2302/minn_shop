package com.thienan.category_service.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(exception=EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception){
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(ErrorResponse
                .builder()
                .status(BAD_REQUEST.value())    
                .error(BAD_REQUEST.name())
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(exception=MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(ErrorResponse
                .builder()
                .status(BAD_REQUEST.value())    
                .error(BAD_REQUEST.name())
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(exception=Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        return ResponseEntity
            .status(BAD_REQUEST)
            .body(ErrorResponse
                .builder()
                .status(BAD_REQUEST.value())    
                .error(BAD_REQUEST.name())
                .message(exception.getMessage())
                .build());
    }


}
