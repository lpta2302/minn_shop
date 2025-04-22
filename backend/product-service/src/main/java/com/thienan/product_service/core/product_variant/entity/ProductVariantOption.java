package com.thienan.product_service.core.product_variant.entity;

import com.thienan.product_service.common.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variant_options")
public class ProductVariantOption extends BaseEntity{
    @NotBlank(message = "name of product variant option can't be null or blank")
    @Size(max = 200, message = "name of product variant option length can't be more than 200 characters")
    private String name;

    @OneToOne(mappedBy="productVariantOption")
    private ProductVariant productVariant;
}
