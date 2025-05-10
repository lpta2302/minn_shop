package com.thienan.product_service.core.stock.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.thienan.product_service.core.stock.dto.StockOptionRequest;
import com.thienan.product_service.core.stock.dto.StockOptionResponse;
import com.thienan.product_service.core.stock.dto.StockOptionValueResponse;
import com.thienan.product_service.core.stock.entity.StockOption;
import com.thienan.product_service.core.stock.entity.StockOptionValue;

@Component
public class StockOptionMapper {

    public StockOption convertToStockOption(StockOptionRequest request, List<StockOptionValue> values) {
        return StockOption.builder()
            .name(request.name())
            .code(request.code())
            .status(request.status())
            .stockOptionValues(values)
            .build();
    }

    public StockOptionResponse convertToStockOptionResponse(StockOption stockOption) {
        List<StockOptionValueResponse> stockOptionValueResponses = 
            stockOption.getStockOptionValues()
            .stream()
            .map(value->
                StockOptionValueResponse
                .builder()
                .id(value.getId())
                .name(value.getName())
                .weightType(value.getWeightType().getName())
                .minWeight(value.getWeightType().getMinWeight())
                .maxWeight(value.getWeightType().getMaxWeight())
                .build())
            .toList();

        return StockOptionResponse
            .builder()
            .id(stockOption.getId())
            .code(stockOption.getCode())
            .name(stockOption.getName())
            .stockOptionValues(stockOptionValueResponses)
            .status(stockOption.getStatus())
            .build();
    }

    public StockOptionResponse convertToStockOptionResponseExcludeValues(StockOption stockOption) {
        return StockOptionResponse
            .builder()
            .id(stockOption.getId())
            .code(stockOption.getCode())
            .name(stockOption.getName())
            .status(stockOption.getStatus())
            .build();
    }
    
}
