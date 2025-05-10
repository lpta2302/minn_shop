package com.thienan.product_service.core.product.dto;

import com.thienan.product_service.core.category.Category;
import com.thienan.product_service.core.product.enums.ProductStatus;
import com.thienan.product_service.core.product_variant.dto.ProductVariantResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record ProductResponse(
    Long id,

    String code,

    String name,

    String description,

    Category category,

    ProductStatus status,

    List<ProductVariantResponse> productVariants
) {
}
