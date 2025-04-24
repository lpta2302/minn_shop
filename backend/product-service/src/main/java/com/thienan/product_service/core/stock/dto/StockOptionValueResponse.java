package com.thienan.product_service.core.stock.dto;

import lombok.Builder;

@Builder
public record StockOptionValueResponse(
    long id,
    String name, 
    String weightType,
    int minWeight,
    int maxWeight
)  {}
