package com.thienan.order_service.product;

import java.math.BigDecimal;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Builder
@Embeddable
public record Stock(
    Long productVariantId,
    Long stockOptionValueId,
    String variantName,
    String sku,
    BigDecimal finalPrice,
    BigDecimal originalPrice,
    String thumbnailUrl,
    String stockOptionValueName
) {
    
}
