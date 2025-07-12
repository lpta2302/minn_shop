package com.thienan.account_service.product;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Builder;

@Embeddable
@Builder
public record ProductVariant(
    Long id,
    @Schema(accessMode=READ_ONLY)
    String variantId,
    @Schema(accessMode=READ_ONLY)
    String slug,
    @Schema(accessMode=READ_ONLY)
    String name,
    @Schema(accessMode=READ_ONLY)
    BigDecimal finalPrice,
    @Schema(accessMode=READ_ONLY)
    String thumbnail,
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(column = @Column(name = "product_option_id"), name = "id"),
        @AttributeOverride(column = @Column(name = "product_option_name"), name = "name")
    })
    @Schema(accessMode=READ_ONLY)
    ProductOption productOption,
    @Schema(accessMode=READ_ONLY)
    ProductVariantStatus status
) {}
