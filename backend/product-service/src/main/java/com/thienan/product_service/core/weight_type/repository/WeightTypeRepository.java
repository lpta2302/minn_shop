package com.thienan.product_service.core.weight_type.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.weight_type.entity.WeightType;


public interface WeightTypeRepository extends JpaRepository<WeightType, Long> {
    @Query("""
        select wt
        from WeightType wt
        where (:name is null or wt.name like :name%)
        and (:code is null or wt.code like :code%)
        and (:min is null or wt.min >= :min)
        and (:max is null or wt.max <= :max)
        """)
    Page<WeightType> search(Pageable pageable, String name, String code, Integer min, Integer max);
}
