package com.thienan.order_service.order.dto;

public record OrderItemsAvailabilityResponse(
    boolean isAvailable,
    String message
) {}
