package com.thienan.account_service.cart.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
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
    private Long productVariantId;
    @NotNull
    private Long stockOptionValueId;
    @NotNull
    private Long quantity;
    
    // @NotNull
    @Schema(accessMode=READ_ONLY)
    private BigDecimal price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cart cart;
}

