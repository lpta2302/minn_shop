package com.thienan.file_service.handler.exceptions.common;

public class EntityNotFoundByIDException extends RuntimeException{

    public EntityNotFoundByIDException(String entityType , String id) {
        super(String.format("%s with ID %s not found", entityType, id));
    }
    
}
