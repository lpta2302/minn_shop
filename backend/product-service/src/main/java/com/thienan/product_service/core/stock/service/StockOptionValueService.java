package com.thienan.product_service.core.stock.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.stock.dto.StockOptionValueRequest;
import com.thienan.product_service.core.stock.dto.StockOptionValueResponse;
import com.thienan.product_service.core.stock.entity.StockOptionValue;
import com.thienan.product_service.core.stock.repository.StockOptionValueRepository;
import com.thienan.product_service.core.weight_type.entity.WeightType;
import com.thienan.product_service.core.weight_type.service.WeightTypeService;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundByIDException;

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

        return requests.stream()
        .map(req -> {
            StockOptionValue value = StockOptionValue
                .builder()
                .name(req.name())
                .weightType(weightTypeMap.get(req.weightTypeId()))
                .build();
            return value;
        })
        .toList();
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

    public PageResponse<StockOptionValueResponse> findAllDeleted(Pageable pageable) {
        var pageResult = stockOptionValueRepository.findAllDeleted(pageable);
        return PageResponse.fromPage(pageResult,
                pageResult.stream().map(
                    stockOptionValue -> StockOptionValueResponse
                        .builder()
                        .id(stockOptionValue.getId())
                        .name(stockOptionValue.getName())
                        .minWeight(stockOptionValue.getWeightType().getMinWeight())
                        .maxWeight(stockOptionValue.getWeightType().getMaxWeight())
                        .weightType(stockOptionValue.getWeightType().getName())
                        .build()
                ).toList()
        );
    }

    public StockOptionValue findReferenceById(Long id) {
        if (!stockOptionValueRepository.existsById(id)) {
            throw new EntityNotFoundByIDException("Stock option value", id.toString());
        }
        return stockOptionValueRepository.getReferenceById(id);
    }

    public StockOptionValueResponse findBriefDetailById(Long stockOptionValueId) {
        return stockOptionValueRepository.findBriefDetailById(stockOptionValueId)
            .orElseThrow(()-> new EntityNotFoundByIDException("Stock option value", stockOptionValueId.toString()));
    }

    public List<StockOptionValue> findAllById(Iterable<Long> ids){
        return stockOptionValueRepository.findAllById(ids);
    }
}
