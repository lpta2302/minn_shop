package com.thienan.product_service.core.stock.dto;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record StockUpdateDetailRequest(
    @Size(max = 100, message = "length of stock sku can't be more than 100 characters")
    String sku,
    @PositiveOrZero(message="quantity must be positive or zero")
    int quantity
) {
}
