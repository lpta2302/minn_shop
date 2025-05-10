package com.thienan.product_service.pipelines.validator;

import java.util.ArrayList;
import java.util.List;

public class ValidatorPipeline<T> {
    private final List<ValidatorStep<T>> steps = new ArrayList<>();

    public ValidatorPipeline<T> add(ValidatorStep<T> step) {
        steps.add(step);
        return this;
    }

    public void validate(T value) {
        for (ValidatorStep<T> step : steps) {
            step.validate(value);
        }
    }
}
