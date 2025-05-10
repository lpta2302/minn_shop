package com.thienan.product_service.core.product.validator;

import com.thienan.product_service.core.product.repository.ProductRepository;
import com.thienan.product_service.handler.exceptions.common.ValidationException;
import com.thienan.product_service.pipelines.validator.ValidatorStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProductValidatorSteps {
    private final ProductRepository productRepository;

    public <T> ValidatorStep<T> checkUniqueCode(Function<T, String> extractor){
        return value -> {
            String code = extractor.apply(value);
            boolean isDuplicatedCode = productRepository.existsByCode(code);
            if (isDuplicatedCode)
                throw new ValidationException(String.format("Duplicated code %s", code));
        };
    }
}
