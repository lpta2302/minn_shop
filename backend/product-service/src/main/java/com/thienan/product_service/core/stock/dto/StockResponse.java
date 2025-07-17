package com.thienan.product_service.core.stock.dto;

import lombok.Builder;

@Builder
public record StockResponse (
    Long productVariantId,
    Long stockOptionValueId,
    String stockOptionValueName,
    String sku,
    int quantity,
    int reservedQuantity,
    int soldQuantity
)
{
}
