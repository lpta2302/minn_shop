package com.thienan.product_service.core.stock.dto;

import lombok.Builder;

@Builder
public record StockResponse (
    Long productVariantId,
    Long stockOptionValueId,
    String sku,
    int quantity,
    int soldQuantity
)
{
}
