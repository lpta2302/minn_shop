package com.thienan.product_service.core.stock.dto;

import java.math.BigDecimal;

import com.thienan.product_service.core.product_variant.utils.PriceCalculator;

import lombok.Builder;

@Builder
public record StockWithVariantAndOptionValue(
    Long productVariantId,
    Long stockOptionValueId,
    String variantName,
    String sku,
    BigDecimal originalPrice,
    Float discount,
    BigDecimal fixedPrice,
    String thumbnailUrl,
    String stockOptionValueName,
    Integer quantity
) {
    public BigDecimal getFinalPrice() {
        return PriceCalculator.calculateFinalPrice(originalPrice, discount, fixedPrice);
    }
}
