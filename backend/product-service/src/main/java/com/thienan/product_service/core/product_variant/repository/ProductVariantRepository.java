package com.thienan.product_service.core.product_variant.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @Query("""
        select pv
        from ProductVariant pv
        where pv.status = 'ACTIVE'     
    """)
    Page<ProductVariant> findAllDisplayed(Pageable pageable);

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


    @Query("""
        select pv
        from ProductVariant pv
        join pv.stocks st
        where pv.id = :productVariantId     
    """)
    Optional<ProductVariant> findProductVariantFullDetailById(long productVariantId);
}
