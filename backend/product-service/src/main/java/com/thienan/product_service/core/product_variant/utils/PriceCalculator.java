package com.thienan.product_service.core.product_variant.utils;

import java.math.BigDecimal;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;

public class PriceCalculator {
    public static BigDecimal calculateFinalPrice(ProductVariant productVariant){
        var originalPrice = productVariant.getOriginalPrice();

        if (productVariant.getDiscount() != null) {
            return originalPrice.multiply(BigDecimal.valueOf((1 - productVariant.getDiscount())));
        } else if(productVariant.getPrice() != null && productVariant.getPrice().compareTo(originalPrice) < 0) {
            return originalPrice.subtract(productVariant.getPrice());
        } else {
            return originalPrice;
        }
    }    
}
