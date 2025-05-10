package com.thienan.product_service.core.stock.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record StockRequest(
        @PositiveOrZero(message = "product variant id must be positive number or zero")
        Long productVariantId,
        @PositiveOrZero(message = "stock option value id must be positive number or zero")
        Long stockOptionValueId,
        @Size(max = 100, message = "length of stock sku can't be more than 100 characters")
        String sku,
        @PositiveOrZero(message="quantity must be positive or zero")
        int quantity
) {
}
