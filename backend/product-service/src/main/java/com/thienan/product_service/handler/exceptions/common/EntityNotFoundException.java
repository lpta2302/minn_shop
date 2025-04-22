package com.thienan.product_service.handler.exceptions.common;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityType ,Long id) {
        super(String.format("%s with ID %d not found", entityType, id));
    }
    
}
