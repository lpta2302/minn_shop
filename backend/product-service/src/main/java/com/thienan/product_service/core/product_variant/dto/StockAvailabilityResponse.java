package com.thienan.product_service.core.product_variant.dto;

import com.thienan.product_service.core.stock.dto.StockOptionValueResponse;

import lombok.Builder;

@Builder
public record StockAvailabilityResponse(
    ProductVariantResponse productVariant,
    StockOptionValueResponse stockOptionValue,
    boolean isAvailable,
    int availableStock,
    String message
) {}
