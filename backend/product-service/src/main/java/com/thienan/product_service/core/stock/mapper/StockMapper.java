package com.thienan.product_service.core.stock.mapper;

import com.thienan.product_service.core.stock.dto.StockResponse;
import com.thienan.product_service.core.stock.entity.Stock;
import org.springframework.stereotype.Component;

@Component
public class StockMapper {
    public StockResponse convertToStockResponse(Stock stock){
        return StockResponse
            .builder()
            .productVariantId(stock.getStockId().getProductVariant().getId())
            .productVariantId(stock.getStockId().getStockOptionValue().getId())
            .sku(stock.getSku())
            .quantity(stock.getQuantity())
            .soldQuantity(stock.getSoldQuantity())
            .build();
    }
}
