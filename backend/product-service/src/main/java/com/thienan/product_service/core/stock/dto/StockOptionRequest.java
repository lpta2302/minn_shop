package com.thienan.product_service.core.stock.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.thienan.product_service.core.stock.enums.StockOptionStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StockOptionRequest(
    @Size(max = 100, message = "length of stock option code can't be more than 100 characters")
    String code,
    @NotBlank(message = "name of stock option can't be null or blank")
    @Size(max = 200, message = "name of stock option length can't be more than 200 characters")
    String name,
    @Validated
    List<StockOptionValueRequest> stockOptionValues,
    StockOptionStatus status
) {}
