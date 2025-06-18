package com.thienan.product_service.core.product_variant.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thienan.product_service.core.product_variant.entity.ProductVariantImage;
import com.thienan.product_service.core.product_variant.enumeration.ProductVariantStatus;
import com.thienan.product_service.core.stock.dto.StockResponse;

import lombok.Builder;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductVariantResponse(
    Long id,
    Long productId,
    String variantId,
    String slug,
    String name,
    BigDecimal price,
    BigDecimal originalPrice,
    float discount,
    int soldQuantity,
    List<ProductVariantImage> productVariantImages,
    List<StockResponse> stocks,
    ProductOptionResponse productOption,
    ProductVariantStatus status
) {
}
