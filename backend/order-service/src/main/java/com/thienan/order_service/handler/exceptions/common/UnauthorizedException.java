package com.thienan.order_service.handler.exceptions.common;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("Unauthorized");
    }
}
