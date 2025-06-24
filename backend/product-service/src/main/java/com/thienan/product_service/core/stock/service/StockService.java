package com.thienan.product_service.core.stock.service;

import static java.lang.String.format;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.product_variant.dto.ProductAvailabilityResponse;
import com.thienan.product_service.core.product_variant.service.ProductVariantService;
import com.thienan.product_service.core.stock.dto.StockRequest;
import com.thienan.product_service.core.stock.dto.StockResponse;
import com.thienan.product_service.core.stock.dto.StockUpdateDetailRequest;
import com.thienan.product_service.core.stock.entity.Stock;
import com.thienan.product_service.core.stock.entity.StockId;
import com.thienan.product_service.core.stock.mapper.StockMapper;
import com.thienan.product_service.core.stock.repository.StockRepository;
import com.thienan.product_service.handler.exceptions.common.EntityNotFoundByIDException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockService {

    private final ProductVariantService productVariantService;
    private final StockOptionValueService stockOptionValueService;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public StockId createAndSave(StockRequest stockRequest) {
        var productVariant = productVariantService.getReferenceById(stockRequest.productVariantId());
        var stockOptionValue = stockOptionValueService.findReferenceById(stockRequest.stockOptionValueId());

        // Stock newStock = new Stock(productVariant, stockOptionValue);
        // newStock.setSku(stockRequest.sku());
        // newStock.setQuantity(stockRequest.quantity());

        var stockId = StockId.builder()
            .productVariant(productVariant)
            .stockOptionValue(stockOptionValue)
            .build();

        Stock newStock = Stock
            .builder()
            .stockId(stockId)
            .sku(stockRequest.sku())
            .quantity(stockRequest.quantity())
            .build();

        return stockRepository.save(newStock).getStockId();
    }

    public String updateInformation(
        Long productVariantId,
        Long stockOptionValueId,
        StockUpdateDetailRequest request){
        var updatingStock = findById(productVariantId, stockOptionValueId);

        if (!updatingStock.getSku().equals(request.sku())) {
            updatingStock.setSku(request.sku());
        }
        updatingStock.setQuantity(request.quantity());
        stockRepository.save(updatingStock);
        return String.format("productVariantId: %d, stockOptionValueId: %d", productVariantId, stockOptionValueId);
    }

    public Stock findById(Long productVariantId, Long stockOptionValueId){
        return stockRepository.findById(productVariantId, stockOptionValueId);
    }

    public StockResponse findFullDetailById(Long productVariantId, Long stockOptionValueId) {
        return stockRepository.findDetailById(productVariantId, stockOptionValueId)
            .orElseThrow(()->new EntityNotFoundByIDException("stock",
                format("product variant id: %d, stock option value id: %d",
                    productVariantId, stockOptionValueId)));
    }

    public PageResponse<StockResponse> findAll(Pageable pageable) {
        var pageResult = stockRepository.findAll(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream()
                .map(stockMapper::convertToStockResponse)
                .toList());
    }

    public PageResponse<StockResponse> search(
        Pageable pageable,
        String sku,
        Integer minQuantity,
        Integer maxQuantity,
        Integer minSoldQuantity,
        Integer maxSoldQuantity) {
        return PageResponse.fromPage(
            stockRepository.search(pageable, sku, minQuantity, maxQuantity, minSoldQuantity, maxSoldQuantity)
        );
    }

    public PageResponse<StockResponse> findAllDeleted(Pageable pageable) {
        var pageResult = stockRepository.findAllDeleted(pageable);
        return PageResponse.fromPage(pageResult,
            pageResult.stream().map(
                tuple ->
                    StockResponse.builder()
                    .productVariantId(tuple.get(0, long.class))
                    .stockOptionValueId(tuple.get(1, long.class))
                    .sku(tuple.get(1, String.class))
                    .quantity(tuple.get(2, int.class))
                    .soldQuantity(tuple.get(3,int.class))
                    .build()
            ).toList());
    }

    public void softDeleteById(Long productVariantId, Long stockOptionValueId) {
        stockRepository.deleteById(productVariantId, stockOptionValueId);
    }

    public void hardDeleteById(Long productVariantId, Long stockOptionValueId) {
        stockRepository.hardDeleteById(productVariantId, stockOptionValueId);
    }

    public ProductAvailabilityResponse checkProductAvailability(Long productVariantId, Long stockOptionValueId, Long quantity) {
        var validQuantity =  stockRepository.checkStockAvailability(productVariantId, stockOptionValueId, quantity)
            .orElseThrow(()->new EntityNotFoundByIDException(
                "Stock", 
                format("product variant id: %d, stock option value id: %d", productVariantId,stockOptionValueId)));

        boolean isValid = quantity <= validQuantity;

        var response = ProductAvailabilityResponse.builder()
            .available(isValid)
            .availableStock(validQuantity)
            .message(
                isValid ?
                "Product available" :
                format("Out of valid quantity: %d", validQuantity)
            )
            .build();
        return response;
    }
}
