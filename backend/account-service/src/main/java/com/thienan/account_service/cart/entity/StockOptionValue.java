package com.thienan.account_service.cart.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;
import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
@Builder
public record StockOptionValue(
    long id,
    @Schema(accessMode=READ_ONLY)
    String name
)  {}
