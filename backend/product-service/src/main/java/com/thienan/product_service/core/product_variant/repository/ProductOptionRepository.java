package com.thienan.product_service.core.product_variant.repository;

import com.thienan.product_service.core.product_variant.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    List<ProductOption> findAllByNameIgnoreCaseIn(List<String> names);

    Optional<ProductOption> findByNameIgnoreCase(String name);

    @Query("""
        select po.id
        from ProductOption po
        where po.name = :productOptionName
        """)
    Optional<Long> findIdByName(String productOptionName);
}
