package com.thienan.product_service.core.stock.dto;

import lombok.Builder;

@Builder
public record StockVariantOptionValuePair(
    Long productVariantId,
    Long stockOptionValueId
) {}
