package com.thienan.product_service.core.product_variant.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
public class ProductVariantImage{
    @Id
    @GeneratedValue
    private Long id;
    private Long fileId;
    @Size(max=200, message="Image key mustn't exceed 200 characters")
    private String key;
    @Size(max=200, message="Image name mustn't exceed 200 characters")
    private String name;
    @Size(min=1, max=2000, message="Image Url mustn't be blank and not exceed 2000 characters")
    private String url;
    private int position;
    private boolean isThumbnail;
}
