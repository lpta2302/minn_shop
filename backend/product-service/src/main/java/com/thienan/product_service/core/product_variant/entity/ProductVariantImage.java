package com.thienan.product_service.core.product_variant.entity;

import jakarta.persistence.Entity;
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
public class ProductVariantImage{
    private Long id;
    @Size(max=200, message="Image name mustn't exceed 2000 characters")
    private String name;
    @Size(min=1, max=2000, message="Image Url mustn't be blank and not exceed 2000 characters")
    private String url;
    private int position;
    private boolean isThumbnail;
}
