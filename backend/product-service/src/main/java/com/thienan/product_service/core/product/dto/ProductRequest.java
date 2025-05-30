package com.thienan.product_service.core.product.dto;

import java.util.List;

import com.thienan.product_service.core.product.enums.ProductStatus;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    @Size(max = 100, message = "product productCode length can't be more than 100 characters")
    String code,

    @NotBlank(message = "product name can't be null or blank")
    @Size(max = 200, message = "product name length can't be more than 200 characters")
    String name,

    @Size(max = 2000, message = "product description length can't be more than 2000 characters")
    String description,

    Long categoryId,

    ProductStatus status,

    List<ProductVariantRequest> productVariants
) {
}
