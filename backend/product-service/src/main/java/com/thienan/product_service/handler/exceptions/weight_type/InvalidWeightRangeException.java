package com.thienan.product_service.handler.exceptions.weight_type;

public class InvalidWeightRangeException extends RuntimeException {
    public InvalidWeightRangeException(int min, int max) {
        super(String.format("Invalid weight range: min [%d] must be less than or equal to max [%d].", min, max));
    }
}
