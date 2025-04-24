package com.thienan.product_service.core.stock.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.stock.dto.StockOptionRequest;
import com.thienan.product_service.core.stock.dto.StockOptionResponse;
import com.thienan.product_service.core.stock.dto.StockOptionValueRequest;
import com.thienan.product_service.core.stock.entity.StockOption;
import com.thienan.product_service.core.stock.entity.StockOptionValue;
import com.thienan.product_service.core.stock.mapper.StockOptionMapper;
import com.thienan.product_service.core.stock.repository.StockOptionRepository;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockOptionService {
    private final StockOptionRepository stockOptionRepository;
    private final StockOptionMapper stockOptionMapper;
    private final StockOptionValueService stockOptionValueService;

    public Long createAndSave(StockOptionRequest request){
        List<StockOptionValue> stockOptionValues = 
            stockOptionValueService.create(request.stockOptionValues());

        StockOption newStockOption = StockOption.builder()
            .code(request.code())
            .name(request.name())
            .status(request.status())
            .build();

        newStockOption.setStockOptionValues(stockOptionValues);
        
        return stockOptionRepository.save(newStockOption).getId();
    }

    public Long updateInformation(Long id, StockOptionRequest request) {
        final String code = request.code();
        final String name = request.name();

        var stockOption = findById(id);
        if (!stockOption.getCode().equals(code)) {
            stockOption.setCode(code);
        }
        stockOption.setName(name);

        return stockOptionRepository.save(stockOption).getId();
    }

    public Long addStockOptionValue(Long id, StockOptionValueRequest request) {
        var stockOption = findById(id);
        var value = stockOptionValueService.create(request);

        stockOption.addStockOptionValue(value);

        return stockOptionRepository.save(stockOption).getId();
    }

    public Long updateStockOptionValue(Long id, Long valueId,
            StockOptionValueRequest request) {
        var stockOption = findById(id);

        StockOptionValue stockOptionValue = stockOption.getStockOptionValues().stream()
            .filter(
                value->value.getId().equals(valueId))
            .findFirst()
            .orElseThrow(()->new EntityNotFoundException("Stock option value", valueId));
        
        stockOptionValueService.update(stockOptionValue, request);

        return stockOptionRepository.save(stockOption).getId();
    }

    public void softDeleteStockOptionValue(Long id, Long valueId) {
        var stockOption = findById(id);
        
        boolean isDeleted = stockOption.getStockOptionValues().removeIf(value -> value.getId().equals(valueId));
        if (!isDeleted) {
            throw new EntityNotFoundException("Stock option value", valueId);
        }

        stockOptionRepository.save(stockOption);
    }

    public void hardDeleteStockOptionValue(Long id, Long valueId) {
        stockOptionValueService.hardDeleteById(valueId);
    }

    public void softDeleteById(Long id){
        stockOptionRepository.deleteById(id);
    }

    public void hardDeleteById(Long id){
        stockOptionRepository.deleteById(id);
    }

    public StockOption findById(Long id){
        var stockOption = stockOptionRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Weight type", id));
        return stockOption;
    }

    public StockOptionResponse findDetailById(Long id){
        var stockOption = stockOptionRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Weight type", id));
        return stockOptionMapper.convertToStockOptionResponse(stockOption);
    }

    public PageResponse<StockOptionResponse> findAll(Pageable pageable){
        var pageResult = stockOptionRepository.findAll(pageable);
        return PageResponse.fromPage(pageResult, 
            pageResult.stream().map(stockOptionMapper::convertToStockOptionResponseExcludeValues).toList()
        );
    }

    public PageResponse<StockOptionResponse> search(
        Pageable pageable, 
        String name, 
        String code){
        var pageResult = stockOptionRepository.search(pageable, name, code);
        return PageResponse.fromPage(pageResult, 
            pageResult.stream().map(stockOptionMapper::convertToStockOptionResponseExcludeValues).toList()
        );
    }
}
