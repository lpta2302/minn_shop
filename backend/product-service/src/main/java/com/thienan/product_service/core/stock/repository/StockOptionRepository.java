package com.thienan.product_service.core.stock.repository;

import com.thienan.product_service.core.stock.dto.StockOptionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.stock.entity.StockOption;

public interface StockOptionRepository extends JpaRepository<StockOption, Long>{

    @Query("""
        select wt
        from WeightType wt
        where wt.deleted = false
        and (:name is null or wt.name like :name%)
        and (:code is null or wt.code like :code%)
        """)
    Page<StockOption> search(Pageable pageable, String name, String code);

    @Query(value = """
       delete from stock_options so where so.id = :id
    """, nativeQuery = true)
    @Modifying
    void hardDeleteById(Long id);

    @Query(value = """
        select so
        from stock_options so
        where so.deleted = true
    """, nativeQuery = true)
    Page<StockOption> findAllDeleted(Pageable pageable);
}
