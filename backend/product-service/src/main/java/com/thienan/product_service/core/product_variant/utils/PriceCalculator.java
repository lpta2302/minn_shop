package com.thienan.product_service.core.product_variant.utils;

import java.math.BigDecimal;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;

public class PriceCalculator {
    public static BigDecimal calculateFinalPrice(ProductVariant productVariant){
        return calculateFinalPrice(productVariant.getOriginalPrice(), productVariant.getDiscount(), productVariant.getPrice());
    }    
    public static BigDecimal calculateFinalPrice(BigDecimal originalPrice, Float discount, BigDecimal fixedDiscount){
        if (discount != null) {
            return originalPrice.multiply(BigDecimal.valueOf((1 - discount)));
        } else if(fixedDiscount != null && fixedDiscount.compareTo(originalPrice) < 0) {
            return originalPrice.subtract(fixedDiscount);
        } else {
            return originalPrice;
        }
    }    
}
