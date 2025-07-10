package com.thienan.account_service.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thienan.account_service.product.ProductVariant;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "cart_items")
@Entity
public class CartItem {
    @Id
    @GeneratedValue
    @Schema(accessMode=READ_ONLY)
    private Long id;
    
    @Version
    @Schema(accessMode=READ_ONLY)
    private Long version;
    
    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "product_variant_id")),
        @AttributeOverride(name = "name", column = @Column(name = "product_variant_name")),
        @AttributeOverride(name = "status", column = @Column(name = "product_variant_status")),
    })
    private ProductVariant productVariant;
    
    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "stock_option_value_id")),
        @AttributeOverride(name = "name", column = @Column(name = "stock_option_value_name")),
    })
    private StockOptionValue stockOptionValue;
    
    @NotNull
    private Long quantity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;
}

