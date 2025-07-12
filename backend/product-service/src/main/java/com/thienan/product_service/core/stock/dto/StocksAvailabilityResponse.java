package com.thienan.product_service.core.stock.dto;

import lombok.Builder;

@Builder
public record StocksAvailabilityResponse(
    boolean isAvailable,
    String message
) {}
