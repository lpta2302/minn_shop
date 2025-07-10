package com.thienan.product_service.core.stock.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;
import com.thienan.product_service.core.product_variant.entity.ProductVariantImage;
import com.thienan.product_service.core.stock.dto.StockVariantOptionValuePair;
import com.thienan.product_service.core.stock.dto.StockWithVariantAndOptionValue;
import com.thienan.product_service.core.stock.entity.Stock;
import com.thienan.product_service.core.stock.entity.StockOptionValue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class StockRepositoryCustomImpl implements StockRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StockWithVariantAndOptionValue> findAllByVariantOptionIds(
        List<StockVariantOptionValuePair> keys) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockWithVariantAndOptionValue> query = cb.createQuery(StockWithVariantAndOptionValue.class);
        Root<Stock> root = query.from(Stock.class);

        Join<Stock, ProductVariant> variantJoin = root.join("stockId").join("productVariant");
        Join<ProductVariant, ProductVariantImage> thumbnailJoin = variantJoin.join("thumbnailImage", JoinType.LEFT);
        Path<StockOptionValue> optionJoin = root.get("stockId").get("stockOptionValue");

        List<Predicate> predicates = new ArrayList<>();

        for (StockVariantOptionValuePair key : keys) {
            Predicate variantMatch = cb.equal(
                root.get("stockId").get("productVariant").get("id"), key.productVariantId()
            );
            Predicate optionMatch = cb.equal(
                root.get("stockId").get("stockOptionValue").get("id"), key.stockOptionValueId()
            );

            predicates.add(cb.and(variantMatch, optionMatch));
        }

        query
        .select(cb.construct(
            StockWithVariantAndOptionValue.class,
            variantJoin.get("id"),
            optionJoin.get("id"),
            variantJoin.get("name"),
            root.get("sku"),
            variantJoin.get("originalPrice"),
            variantJoin.get("discount"),
            variantJoin.get("price"),
            thumbnailJoin.get("url"),
            optionJoin.get("name"),
            root.get("quantity")
        ))
        .where(cb.or(predicates.toArray(Predicate[]::new)));
    
        return entityManager.createQuery(query).getResultList();

    }
    
}
