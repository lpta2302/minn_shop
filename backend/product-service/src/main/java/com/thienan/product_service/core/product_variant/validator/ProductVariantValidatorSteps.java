package com.thienan.product_service.core.product_variant.validator;

import static java.lang.String.format;
import java.math.BigDecimal;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;
import com.thienan.product_service.core.product_variant.repository.ProductVariantRepository;
import com.thienan.product_service.handler.exceptions.common.ValidationException;
import com.thienan.product_service.pipelines.validator.ValidatorPipeline;
import com.thienan.product_service.pipelines.validator.ValidatorStep;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductVariantValidatorSteps {
    private final ProductVariantRepository productVariantRepository;

    public <T>ValidatorStep<T> hasUniqueVariantId(
        Function<T, String> extractor){
        return value -> {
            String variantId = extractor.apply(value);
            if (variantId == null)
                return;
            boolean isExisted = productVariantRepository.existsByVariantId(variantId);
            if (isExisted){
                throw new ValidationException(
                    format("product variant id: [%s] has existed already", variantId));
            }
        };
    }

    public <T>ValidatorStep<T> hasUniqueSlug(
        Function<T, String> extractor){
        return value -> {
            String slug = extractor.apply(value);
            log.info(slug);
            if (slug == null)
                return;
            boolean isExisted = productVariantRepository.existsBySlug(slug);
            if (isExisted){
                throw new ValidationException(
                    format("slug of product variant: [%s] has existed already", slug));
            }
        };
    }

    public <T>ValidatorStep<T> hasUniqueProductAndProductOption(
        Long productId,
        Function<T, String> productOptionNameExtractor){
        return value -> {
            String productOptionName = productOptionNameExtractor.apply(value);
            if (productId == null)
                throw new ValidationException("product id can't be null");
            if (productOptionName == null)
                throw new ValidationException("product option name can't be null");

            boolean isExisted = productVariantRepository.existsByProduct_IdAndProductOption_Name(productId, productOptionName);
            if (isExisted){
                throw new ValidationException(
                    format("product option [%s] has existed in product has id: [%d]", productOptionName, productId));
            }
        };
    }

    public <T>ValidatorStep<T> hasDiscountAndPriceAfterAllSet(
        Function<T, Float> discountExtractor,
        Function<T, BigDecimal> priceAfterExtractor){
        return value -> {
            float discount = discountExtractor.apply(value);
            BigDecimal priceAfter = priceAfterExtractor.apply(value);
            if (priceAfter != null && discount <= 0){
                throw new ValidationException("Discount and price after must not be set together");
            }
        };
    }

    public ValidatorPipeline<ProductVariantRequest> getProductVariantRequestValidator(Long productId){
        return new ValidatorPipeline<ProductVariantRequest>()
            .add(hasUniqueVariantId(ProductVariantRequest::variantId))
            .add(hasUniqueSlug(ProductVariantRequest::slug))
            .add(hasUniqueProductAndProductOption(
                productId,
                ProductVariantRequest::productOptionName))
            .add(
                hasDiscountAndPriceAfterAllSet(
                    ProductVariantRequest::discount,
                    ProductVariantRequest::price)
            );
    } 
}
