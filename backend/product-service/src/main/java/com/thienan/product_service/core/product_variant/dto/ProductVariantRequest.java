package com.thienan.product_service.core.product_variant.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thienan.product_service.core.product_variant.enumeration.ProductVariantStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductVariantRequest(
    @Size(max = 100, message = "variantId of product variant length can't be more than 100 characters")
    String variantId,

    @Size(max = 200, message = "slug of product variant length can't be more than 200 characters")
    String slug,

    @NotBlank(message = "name of product variant can't be null or blank")
    @Size(max = 200, message = "name of product variant length can't be more than 200 characters")
    String name,

    @PositiveOrZero(message="price must be positive or zero")
    BigDecimal price,

    @PositiveOrZero(message="original price must be positive or zero")
    BigDecimal originalPrice,

    @PositiveOrZero(message="discount must be positive or zero")
    float discount,

    String productOptionName,

    @JsonProperty(defaultValue="DRAFT")
    ProductVariantStatus status
)
{
}
