package com.thienan.product_service.core.product_variant.dto;

import lombok.Builder;

@Builder
public record ProductAvailabilityResponse(
    Long productId,
    boolean available,
    int availableStock,
    String message
) {}
