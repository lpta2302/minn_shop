package com.thienan.account_service.product;

import com.thienan.account_service.cart.entity.StockOptionValue;

public record ProductAvailabilityResponse(
    ProductVariant productVariant,
    StockOptionValue stockOptionValue,
    boolean available,
    int availableStock,
    String message
) {}