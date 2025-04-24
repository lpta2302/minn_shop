package com.thienan.product_service.core.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
