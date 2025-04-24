package com.thienan.product_service.core.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.thienan.product_service.core.stock.dto.StockOptionValueRequest;
import com.thienan.product_service.core.stock.entity.StockOptionValue;
import com.thienan.product_service.core.stock.repository.StockOptionValueRepository;
import com.thienan.product_service.core.weight_type.entity.WeightType;
import com.thienan.product_service.core.weight_type.service.WeightTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockOptionValueService {

    private final StockOptionValueRepository stockOptionValueRepository;
    private final WeightTypeService weightTypeService;
    
    public List<StockOptionValue> create(List<StockOptionValueRequest> requests) {
        Map<Long,WeightType> weightTypeMap = new HashMap<>();

        weightTypeService.findAllById(requests.stream().map(StockOptionValueRequest::weightTypeId).toList())
            .forEach(wt->weightTypeMap.put(wt.getId(), wt));

        List<StockOptionValue> result = requests.stream()
        .map(req -> {
            StockOptionValue value = StockOptionValue
                .builder()
                .name(req.name())
                .weightType(weightTypeMap.get(req.weightTypeId()))
                .build();
            return value;
        })
        .toList();
        return result;
    }

    public StockOptionValue create(StockOptionValueRequest request) {
        WeightType weightType = null;
        
        if (request.weightTypeId() != null) {
            weightType = weightTypeService.findById(request.weightTypeId());
        }
        return StockOptionValue.builder()
            .name(request.name())
            .weightType(weightType)
            .build();
    }

    public StockOptionValue update(StockOptionValue stockOptionValue, StockOptionValueRequest request) {
        stockOptionValue.setName(request.name());

        if (!stockOptionValue.getWeightType().getId().equals(request.weightTypeId())) {
            var weightType = weightTypeService.findById(request.weightTypeId());
            stockOptionValue.setWeightType(weightType);
        }

        return stockOptionValue;
    }

    public void hardDeleteById(Long valueId) {
        stockOptionValueRepository.hardDeleteById(valueId);
    }
}
