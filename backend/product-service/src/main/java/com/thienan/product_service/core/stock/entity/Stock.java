package com.thienan.product_service.core.stock.entity;

import java.time.LocalDate;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.thienan.product_service.core.product_variant.entity.ProductVariant;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "stocks")
@SoftDelete(columnName="deleted", converter= TrueFalseConverter.class)
public class Stock {
    public Stock(ProductVariant productVariant, StockOptionValue stockOptionValue) {
        this.stockId = StockId.builder()
            .productVariant(productVariant)
            .stockOptionValue(stockOptionValue)
            .build();
    }

    @EmbeddedId
    private StockId stockId;

    @Column(unique = true)
    @Size(max = 100, message = "sku of product variant length can't be more than 100 characters")
    private String sku;

    @Version
    private Long version;

    @Column(updatable = false, nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifiedDate;

    @Column(insertable = false, updatable = false)
    private boolean deleted;

    @PositiveOrZero(message="quantity must be positive or zero")
    private int quantity;

    @PositiveOrZero(message="sold quantity must be positive or zero")
    private int soldQuantity;
}
