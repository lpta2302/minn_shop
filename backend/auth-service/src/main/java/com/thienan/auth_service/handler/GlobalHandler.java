package com.thienan.auth_service.handler;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.thienan.auth_service.handler.exceptions.common.BadRequestException;
import com.thienan.auth_service.handler.exceptions.common.CustomBadRequestException;
import com.thienan.auth_service.handler.exceptions.common.EntityNotFoundByIDException;
import com.thienan.auth_service.handler.exceptions.common.ValidationException;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(exception= EntityNotFoundException.class)
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

    @ExceptionHandler(exception= EntityNotFoundByIDException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundByIDException(EntityNotFoundByIDException exception){
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

    @ExceptionHandler(exception= MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        var details = exception.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                fieldError -> fieldError.getDefaultMessage() == null ?
                    "Not valid" : fieldError.getDefaultMessage(),
                (existing, _)->existing));
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(BAD_REQUEST.value())
                .details(details)
                .message("Request not valid")
                .build()
            );
    }

    @ExceptionHandler(exception= DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .status(BAD_REQUEST.value())
                .message(exception.getMostSpecificCause().getMessage())
                .build()
            );
    }

    @ExceptionHandler(exception= ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(exception.getStatusCode().toString())
                .status(exception.getStatusCode().value())
                .message(exception.getReason())
                .build()
            );
    }

    @ExceptionHandler(exception= CustomBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleCustomBadRequestException(CustomBadRequestException exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(HttpStatus.BAD_REQUEST.name())
                .details(exception.getDetails())
                .status(BAD_REQUEST.value())
                .message(exception.getReason())
                .build()
            );
    }

    @ExceptionHandler(exception= BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception){
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

    @ExceptionHandler(exception= ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(BAD_REQUEST.name())
                .status(BAD_REQUEST.value())
                .message(exception.getMessage())
                .build()
            );
    }

    @ExceptionHandler(exception=Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        return ResponseEntity
            .badRequest()
            .body(
                ErrorResponse.builder()
                .error(INTERNAL_SERVER_ERROR.name())
                .status(INTERNAL_SERVER_ERROR.value())
                .message("Some thing went wrong in server!")
                .build()
            );
    }

}
