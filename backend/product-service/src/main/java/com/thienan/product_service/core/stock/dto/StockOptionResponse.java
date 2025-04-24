package com.thienan.product_service.core.stock.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import com.thienan.product_service.core.stock.enums.StockOptionStatus;

import lombok.Builder;

@Builder
@JsonInclude(NON_NULL)
public record StockOptionResponse(
    long id,
    String code,
    String name,
    List<StockOptionValueResponse> stockOptionValues,
    StockOptionStatus status
) {}
