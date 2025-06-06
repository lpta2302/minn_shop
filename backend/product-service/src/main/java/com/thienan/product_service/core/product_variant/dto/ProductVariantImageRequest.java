package com.thienan.product_service.core.product_variant.dto;

public record ProductVariantImageRequest(
    Long id,
    String name,
    String key,
    int position,
    boolean isThumbnail
)  {

}
