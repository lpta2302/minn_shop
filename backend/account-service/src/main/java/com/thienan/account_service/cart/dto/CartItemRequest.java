package com.thienan.account_service.cart.dto;

public record CartItemRequest(
    Long id,
    Long productVariantId,
    Long stockOptionValueId,
    int quantity
) {
    
}
