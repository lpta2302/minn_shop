package com.thienan.product_service.core.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StockOptionValueRequest(
    @NotBlank(message = "name of stock option value can't be null or blank")
    @Size(max = 200, message = "name of stock option value length can't be more than 200 characters")
    String name,
    Long weightTypeId
)  {}
