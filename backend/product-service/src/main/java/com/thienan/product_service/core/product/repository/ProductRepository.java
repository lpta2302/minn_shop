package com.thienan.product_service.core.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        select p
        from Product p
        where p.id = :id
    """)
    @EntityGraph(attributePaths = "productVariants")
    Optional<Product> findWithFullVariantsById(Long id);

    @Query(value = """
        select p.*
        from products p
        where p.deleted = true
    """, nativeQuery = true)
    Page<Product> findAllDeleted(Pageable pageable);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    boolean existsByCode(String code);

    @Query(value = """
        delete from products p
        where p.id = :id
        """, nativeQuery = true)
    @Modifying
    void hardDeleteById(Long id);

    @Query(value = """
        update products
        set deleted = false
        where id = :id
        """, nativeQuery = true)
    @Modifying
    void recoveryById(Long id);

    @Query(value = """
        select pv.id
        from product_variants pv 
        where pv.product_id = :id
        """, nativeQuery = true)
    List<Long> findProductVariants_IdById(Long id);
}
