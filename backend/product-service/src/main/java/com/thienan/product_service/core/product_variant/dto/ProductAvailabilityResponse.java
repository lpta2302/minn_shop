package com.thienan.product_service.core.product_variant.dto;

import com.thienan.product_service.core.stock.dto.StockOptionValueResponse;

import lombok.Builder;

@Builder
public record ProductAvailabilityResponse(
    ProductVariantResponse productVariant,
    StockOptionValueResponse stockOptionValue,
    boolean available,
    int availableStock,
    String message
) {}
