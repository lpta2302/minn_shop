package com.thienan.product_service.core.product.dto;

import com.thienan.product_service.core.product.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductInformationRequest(
    @Size(max = 100, message = "product productCode length can't be more than 100 characters")
    String code,

    @NotBlank(message = "product name can't be null or blank")
    @Size(max = 200, message = "product name length can't be more than 200 characters")
    String name,

    @Size(max = 2000, message = "product description length can't be more than 2000 characters")
    String description,

    Long categoryId,

    ProductStatus status

    ) {
}
