package com.thienan.product_service.core.stock.mapper;

import org.springframework.stereotype.Component;

import com.thienan.product_service.core.stock.dto.StockResponse;
import com.thienan.product_service.core.stock.entity.Stock;

@Component
public class StockMapper {
    public StockResponse convertToStockResponse(Stock stock){
        return StockResponse
            .builder()
            .productVariantId(stock.getStockId().getProductVariant().getId())
            .stockOptionValueId(stock.getStockId().getStockOptionValue().getId())
            .sku(stock.getSku())
            .quantity(stock.getQuantity())
            .soldQuantity(stock.getSoldQuantity())
            .build();
    }
}
