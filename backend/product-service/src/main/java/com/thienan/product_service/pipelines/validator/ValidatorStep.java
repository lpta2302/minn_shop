package com.thienan.product_service.pipelines.validator;


import com.thienan.product_service.handler.exceptions.common.ValidationException;

public interface ValidatorStep <T>{
    void validate(T value) throws ValidationException;
}
