package com.thienan.product_service.core.stock.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.product_variant.dto.StockAvailabilityResponse;
import com.thienan.product_service.core.stock.dto.StockRequest;
import com.thienan.product_service.core.stock.dto.StockResponse;
import com.thienan.product_service.core.stock.dto.StockUpdateDetailRequest;
import com.thienan.product_service.core.stock.dto.StockVariantOptionValuePair;
import com.thienan.product_service.core.stock.dto.StockWithVariantAndOptionValue;
import com.thienan.product_service.core.stock.dto.StocksAvailabilityResponse;
import com.thienan.product_service.core.stock.entity.StockId;
import com.thienan.product_service.core.stock.service.StockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockId> create(
            @Valid
            @RequestBody
            StockRequest stockRequest) {
        return ResponseEntity.ok(stockService.createAndSave(stockRequest));
    }

    @PatchMapping("/{productVariantId}/{stockOptionValueId}")
    public ResponseEntity<String> update(
            @PathVariable Long productVariantId,
            @PathVariable Long stockOptionValueId,
            @Valid @RequestBody StockUpdateDetailRequest request) {
        return ResponseEntity.ok(stockService.updateInformation(productVariantId, stockOptionValueId, request));
    }

    @GetMapping("/{productVariantId}/{stockOptionValueId}")
    public ResponseEntity<StockResponse> findFullDetailById(
        @PathVariable Long productVariantId,
        @PathVariable Long stockOptionValueId) {
        return ResponseEntity.ok(stockService.findFullDetailById(productVariantId, stockOptionValueId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<StockResponse>> findAll(
            @ParameterObject
            @PageableDefault(page=0, size=10)
            Pageable pageable
    ) {
        return ResponseEntity.ok(stockService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<StockResponse>> search(
            @ParameterObject
            @PageableDefault(page=0, size=10)
            Pageable pageable,
            @RequestParam(required = false, name = "sku") String sku,
            @RequestParam(required = false, name = "min-quantity") Integer minQuantity,
            @RequestParam(required = false, name = "max-quantity") Integer maxQuantity,
            @RequestParam(required = false, name = "min-sold-quantity") Integer minSoldQuantity,
            @RequestParam(required = false, name = "max-sold-quantity") Integer maxSoldQuantity) {
        return ResponseEntity.ok(stockService.search(pageable, sku, minQuantity, maxQuantity, minSoldQuantity, maxSoldQuantity));
    }

    @GetMapping("/deleted")
    public ResponseEntity<PageResponse<StockResponse>> findAllDeleted(
            @ParameterObject
            @PageableDefault(page=0, size=10)
            Pageable pageable) {
        return ResponseEntity.ok(stockService.findAllDeleted(pageable));
    }

    @PostMapping("/with-brief-detail")
    public ResponseEntity<List<StockWithVariantAndOptionValue>> findStocksWithBriefDetail (
        @RequestBody List<StockVariantOptionValuePair> pairs) {
        return ResponseEntity.ok(stockService.findStockWithVariantAndOptionValues(pairs));
    }
    

    @DeleteMapping("/{productVariantId}/{stockOptionValueId}")
    public ResponseEntity<Void> softDeleteById(
        @PathVariable Long productVariantId,
        @PathVariable Long stockOptionValueId
    ){
        stockService.softDeleteById(productVariantId, stockOptionValueId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productVariantId}/{stockOptionValueId}/hard-delete")
    public ResponseEntity<Void> hardDeleteById(
        @PathVariable Long productVariantId,
        @PathVariable Long stockOptionValueId
    ){
        stockService.hardDeleteById(productVariantId, stockOptionValueId);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/{productVariantId}/{stockOptionValueId}/available")
    public ResponseEntity<StockAvailabilityResponse> checkStockAvailability(
        @RequestParam(required=true)
        Long quantity,
        @PathVariable
        Long productVariantId,
        @PathVariable
        Long stockOptionValueId
    ){
        return ResponseEntity.ok(
            stockService.checkProductAvailability(productVariantId, stockOptionValueId, quantity)
        );
    }
    
    @PutMapping("/reserve")
    public ResponseEntity<StocksAvailabilityResponse> reserveStock(
        @RequestBody
        List<StockRequest> orderItems
    ){
        return ResponseEntity.ok(
            stockService.reserveStock(orderItems)
        );
    }

    @PutMapping("/deduct")
    public ResponseEntity<StocksAvailabilityResponse> deductStock(
        @RequestBody List<StockRequest> requests) {
        return ResponseEntity.ok(
            stockService.deductStock(requests)
        );
    }
    
}
