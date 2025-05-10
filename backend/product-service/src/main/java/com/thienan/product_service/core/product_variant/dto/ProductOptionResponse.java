package com.thienan.product_service.core.product_variant.dto;

import lombok.Builder;

@Builder
public record ProductOptionResponse(
    Long id,
    String name
) {

}
