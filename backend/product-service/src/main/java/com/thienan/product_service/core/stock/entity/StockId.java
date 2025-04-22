package com.thienan.product_service.core.stock.entity;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;

import jakarta.persistence.Embeddable;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockId {
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="product_variant_id")
    private ProductVariant productVariant;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="stock_option_value_id")
    private StockOptionValue stockOptionValue;
}
