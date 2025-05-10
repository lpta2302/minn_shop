package com.thienan.product_service.core.product_variant.entity;

import com.thienan.product_service.core.product.entity.Product;

import static jakarta.persistence.CascadeType.PERSIST;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductVariantId {
    @OneToMany
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;
}