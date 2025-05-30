package com.thienan.product_service.core.product.specification;

import org.springframework.data.jpa.domain.Specification;

import com.thienan.product_service.core.product.entity.Product;
import com.thienan.product_service.core.product.enums.ProductStatus;

public class ProductSpecification {
    public static Specification<Product> hasName(String name) {
        return (root, _, cb) ->
            name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasCode(String code) {
        return (root, _, cb) ->
            code == null ? null : cb.like(cb.lower(root.get("code")), code.toLowerCase() + "%");
    }

    public static Specification<Product> hasCategoryName(String categoryName) {
        return (root, _, cb) ->
            categoryName == null ? null : cb.equal(root.get("category").get("name"), categoryName);
    }

    public static Specification<Product> hasCategoryCode(String categoryCode) {
        return (root, _, cb) ->
            categoryCode == null ? null : cb.equal(root.get("category").get("code"), categoryCode);
    }

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, _, cb) ->
            categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasStatus(ProductStatus status) {
        return (root, _, cb) ->
            status == null ? null : cb.equal(root.get("status"), status);
    }
}
