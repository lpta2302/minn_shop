package com.thienan.product_service.core.product_variant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thienan.product_service.core.product_variant.entity.ProductVariantImage;

public interface ProductVariantImageRepository extends JpaRepository<ProductVariantImage, Long>{

    Optional<ProductVariantImage> findByKey(String key);
    
}
