package com.thienan.product_service.core.weight_type.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.weight_type.entity.WeightType;


public interface WeightTypeRepository extends JpaRepository<WeightType, Long> {
    @Query("""
        select wt
        from WeightType wt
        where wt.deleted = false
        and (:name is null or wt.name like :name%)
        and (:code is null or wt.code like :code%)
        and (:minWeight is null or wt.minWeight >= :minWeight)
        and (:maxWeight is null or wt.maxWeight <= :maxWeight)
        """)
    Page<WeightType> search(Pageable pageable, String name, String code, Integer minWeight, Integer maxWeight);

    @Query(value="delete from weight_types wt where wt.id = :id ", nativeQuery=true)
    @Modifying
    void hardDeleteById(Long id);

    @Query(value = """
        select *
        from weight_types wt
        where wt.deleted = true 
        """, nativeQuery = true)
    Page<WeightType> findAllDeleted(Pageable pageable);

    @Query(value="""
         update weight_types wt set wt.deleted = false where wt.id = :id
        """,
        nativeQuery = true)
    @Modifying
    WeightType recovery(Long id);
}
