package com.thienan.product_service.core.product_variant.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;

import com.thienan.product_service.common.BaseEntity;
import com.thienan.product_service.core.product.entity.Product;
import com.thienan.product_service.core.product_variant.enumeration.ProductVariantStatus;
import com.thienan.product_service.core.stock.entity.Stock;
import com.thienan.product_service.core.stock.entity.StockId;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import jakarta.persistence.Entity;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Enumerated;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "product_variants",
    uniqueConstraints = {
        @UniqueConstraint(name = "UniqueProductAndProductOption",
            columnNames = {"product", "productOption"})
    }
)
@DynamicUpdate
public class ProductVariant extends BaseEntity {
    @Size(max = 100, message = "variantId of product variant length can't be more than 100 characters")
    private String variantId;

    @Size(max = 200, message = "slug of product variant length can't be more than 200 characters")
    private String slug;

    @NotBlank(message = "name of product variant can't be null or blank")
    @Size(max = 200, message = "name of product variant length can't be more than 200 characters")
    private String name;

    @PositiveOrZero(message="price must be positive or zero")
    private BigDecimal price;

    @PositiveOrZero(message="original price must be positive or zero")
    private BigDecimal originalPrice;

    @PositiveOrZero(message="discount must be positive or zero")
    private Float discount;

    @PositiveOrZero(message="sold quantity must be positive or zero")
    private int soldQuantity;

    @OneToMany(mappedBy="stockId.productVariant")
    private List<Stock> stocks;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @OneToMany(cascade={ ALL })
    private List<ProductVariantImage> images;

    @OneToOne
    private ProductVariantImage thumbnailImage;

    @Enumerated(STRING)
    private ProductVariantStatus status;

    public void setStocks(List<Stock> stocks){
        if (this.stocks == null) {
            this.stocks = new ArrayList<>();
        } else {
            this.stocks.clear();
        }

        stocks.forEach((stock) -> {
            if (stock.getStockId() == null) {
                stock.setStockId(new StockId());
            }
            stock.getStockId().setProductVariant(this);
            this.stocks.add(stock);
        });
    }
}
