package com.thienan.product_service.core.stock.repository;

import com.thienan.product_service.core.stock.dto.StockOptionValueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.stock.entity.StockOptionValue;

public interface StockOptionValueRepository extends JpaRepository<StockOptionValue, Long>{
    @Query(value = """
       delete from stock_option_values sov where sov.id = :id
    """, nativeQuery = true)
    @Modifying
    void hardDeleteById(Long id);

    @Query(value = """
        select sov
        from stock_option_values sov
        where sov.deleted = true
    """, nativeQuery = true)
    Page<StockOptionValue> findAllDeleted(Pageable pageable);
}
