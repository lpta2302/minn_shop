package com.thienan.product_service.core.product.entity;

import java.util.ArrayList;
import java.util.List;

import com.thienan.product_service.common.BaseEntity;
import com.thienan.product_service.core.category.Category;
import com.thienan.product_service.core.product.enums.ProductStatus;
import com.thienan.product_service.core.product_variant.entity.ProductVariant;

import jakarta.persistence.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

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
@Table(name = "products")
public class Product extends BaseEntity{
    @Size(max = 100, message = "product productCode length can't be more than 100 characters")
    @Column(unique = true)
    private String code;

    @NotBlank(message = "product name can't be null or blank")
    @Size(max = 200, message = "product name length can't be more than 200 characters")
    private String name;

    @Size(max = 2000, message = "product description length can't be more than 2000 characters")
    private String description;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "category_id")),
        @AttributeOverride(name = "code", column = @Column(name = "category_code")),
        @AttributeOverride(name = "name", column = @Column(name = "category_name")),
    })
    private Category category;

    @Enumerated(STRING)
    private ProductStatus status;

    @OneToMany(mappedBy="product", cascade = ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants;

    public void setProductVariants(List<ProductVariant> productVariants){
        if (this.productVariants == null) {
            this.productVariants = new ArrayList<>();
        } else {
            this.productVariants.clear();
        }

        productVariants.forEach((productVariant) -> {
            productVariant.setProduct(this);
            this.productVariants.add(productVariant);
        });
    }

    public void addProductVariant(ProductVariant newProductVariant) {
        if (productVariants == null) {
            productVariants = new ArrayList<>();
        }

        productVariants.add(newProductVariant);
        newProductVariant.setProduct(this);
    }
}
