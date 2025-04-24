package com.thienan.product_service.core.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.stock.entity.StockOptionValue;

public interface StockOptionValueRepository extends JpaRepository<StockOptionValue, Long>{
    @Query("""
       delete from StockOptionValue sov where sov.id = :id     
    """)
    void hardDeleteById(Long id);
}
