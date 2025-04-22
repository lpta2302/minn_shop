package com.thienan.product_service.core.product.entity;

import java.util.ArrayList;
import java.util.List;

import com.thienan.product_service.common.BaseEntity;
import com.thienan.product_service.core.product.enums.ProductStatus;
import com.thienan.product_service.core.product_variant.entity.ProductVariant;

import jakarta.persistence.Entity;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
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
@Table(name = "products")
public class Product extends BaseEntity{

    @Size(max = 100, message = "product productCode length can't be more than 100 characters")
    private String code;

    @NotBlank(message = "product name can't be null or blank")
    @Size(max = 200, message = "product name length can't be more than 200 characters")
    private String name;

    @Size(max = 2000, message = "product description length can't be more than 2000 characters")
    private String description;
    
    private Long categoryId;

    @Size(max = 200, message = "product description length can't be more than 200 characters")
    private String categoryName;

    @Enumerated(STRING)
    private ProductStatus status;

    @OneToMany(mappedBy="product")
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
}
