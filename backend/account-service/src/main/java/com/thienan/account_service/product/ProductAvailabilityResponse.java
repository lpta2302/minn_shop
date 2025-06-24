package com.thienan.account_service.product;

public record ProductAvailabilityResponse(
    Long productId,
    boolean available,
    int availableStock,
    String message
) {}