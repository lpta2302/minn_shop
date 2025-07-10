package com.thienan.order_service.order.dto;

import lombok.Builder;

@Builder
public record OrderItemRequest(
    Long productVariantId,
    Long stockOptionValueId,
    Integer quantity
)  {}