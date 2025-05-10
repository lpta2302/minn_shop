package com.thienan.product_service.core.stock.dto;

import lombok.*;

import java.util.Objects;

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
