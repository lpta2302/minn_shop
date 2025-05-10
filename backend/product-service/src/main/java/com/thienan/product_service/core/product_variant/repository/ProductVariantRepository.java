package com.thienan.product_service.core.product_variant.repository;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    @Query(value = """
        update product_variants
        set deleted = false
        where id in :productVariantIds
        """, nativeQuery = true)
    @Modifying
    void recoveryAllById(Collection<Long> productVariantIds);

    boolean existsByVariantId(String variantId);

    boolean existsBySlug(String slug);

    boolean existsByProduct_IdAndProductOption_Name(Long productId, String productOptionName);
}
