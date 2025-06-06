package com.thienan.file_service.handler.exceptions.common;

public class ExistedS3FileKeyException extends RuntimeException {
    public ExistedS3FileKeyException(String message){
        super(message);
    }
}
