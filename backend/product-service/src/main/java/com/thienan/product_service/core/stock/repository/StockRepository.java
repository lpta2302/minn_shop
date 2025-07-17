package com.thienan.product_service.core.stock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.thienan.product_service.core.stock.dto.StockResponse;
import com.thienan.product_service.core.stock.entity.Stock;
import com.thienan.product_service.core.stock.entity.StockId;

import jakarta.persistence.Tuple;

public interface StockRepository extends JpaRepository<Stock, StockId>, StockRepositoryCustom {
    @Query("""
        select s from Stock s
        where s.stockId.productVariant.id = :productVariantId
        and s.stockId.stockOptionValue.id = :stockOptionValueId
    """)
    Stock findById(Long productVariantId, Long stockOptionValueId);
    
    @Query("""
        select s from Stock s
        where s.stockId.productVariant.id in :productVariantIds
        and s.stockId.stockOptionValue.id in :stockOptionValueIds
    """)
    Stock findAllById(Long productVariantId, Long stockOptionValueId);

    @Query("""
        select new com.thienan.product_service.core.stock.dto.StockResponse(
            :productVariantId,
            :stockOptionValueId,
            s.stockId.stockOptionValue.name,
            s.sku,
            s.quantity,
            s.reservedQuantity,
            s.soldQuantity
        )
        from Stock s
        where s.stockId.productVariant.id = :productVariantId
        and s.stockId.stockOptionValue.id = :stockOptionValueId
        """)
        Optional<StockResponse> findDetailById(Long productVariantId, Long stockOptionValueId);
        
    @Query("""
        select new com.thienan.product_service.core.stock.dto.StockResponse(
            :productVariantId,
            s.stockId.stockOptionValue.id,
            s.stockId.stockOptionValue.name,
            s.sku,
            s.quantity,
            s.reservedQuantity,
            s.soldQuantity
        )
        from Stock s
        where s.stockId.productVariant.id = :productVariantId
        """)
    List<StockResponse> findStocksByVariantId(Long productVariantId);

    @Query("""
        select new com.thienan.product_service.core.stock.dto.StockResponse(
            s.stockId.productVariant.id,
            s.stockId.stockOptionValue.id,
            s.stockId.stockOptionValue.name,
            s.sku,
            s.quantity,
            s.reservedQuantity,
            s.soldQuantity
        )
        from Stock s
        where (:sku is null or s.sku like :sku%)
        and (:minQuantity is null or s.quantity >= :minQuantity)
        and (:maxQuantity is null or s.quantity <= :maxQuantity)
        and (:maxSoldQuantity is null or s.soldQuantity <= :maxSoldQuantity)
        and (:minSoldQuantity is null or s.soldQuantity >= :minSoldQuantity)
        """)
    Page<StockResponse> search(Pageable pageable, String sku, Integer minQuantity, Integer maxQuantity, Integer minSoldQuantity, Integer maxSoldQuantity);

    @Query(value = """
        select s.product_variant_id,
            s.stock_option_value_id,
            s.sku,
            s.quantity,
            s.sold_quantity
        from stocks s
        where s.deleted = true
        """, nativeQuery = true)
    Page<Tuple> findAllDeleted(Pageable pageable);

    @Query("""
        delete from Stock s
        where s.stockId.productVariant.id = :productVariantId
        and s.stockId.stockOptionValue.id = :stockOptionValueId
        """)
    @Modifying
    void deleteById(Long productVariantId, Long stockOptionValueId);

    @Query(value = """
        delete from stocks s
        where s.product_variant_id = :productVariantId
        and s.stock_option_value_id = :stockOptionValueId
        """, nativeQuery = true)
    @Modifying
    void hardDeleteById(Long productVariantId, Long stockOptionValueId);

    @Query("""
        select st.quantity
        from Stock st
        where st.stockId.productVariant.id = :productVariantId and
        st.stockId.stockOptionValue.id = :stockOptionValueId
    """)
    Optional<Integer> checkStockAvailability(Long productVariantId, Long stockOptionValueId, Long quantity);

    @Modifying
    @Query("""
        update Stock s
        set s.reservedQuantity = s.reservedQuantity + :quantity
        where s.stockId.productVariant.id = :productVariantId
        and s.stockId.stockOptionValue.id = :stockOptionValueId
        and s.quantity >= (s.reservedQuantity + :quantity)
    """)
    int reserveStock(Long productVariantId, Long stockOptionValueId, int quantity);

    @Modifying
    @Query("""
        update Stock s
        set s.reservedQuantity = s.reservedQuantity - :quantity,
        s.quantity = s.quantity - :quantity,
        s.status = CASE WHEN s.quantity - :quantity <= 0 THEN 'OUT_OF_STOCK' ELSE s.status END
        where s.stockId.productVariant.id = :productVariantId
        and s.stockId.stockOptionValue.id = :stockOptionValueId
        and s.reservedQuantity >= :quantity       
        and s.quantity >= :quantity

    """)
    int deductStock(Long productVariantId,
            Long stockOptionValueId,
            int quantity);

}
