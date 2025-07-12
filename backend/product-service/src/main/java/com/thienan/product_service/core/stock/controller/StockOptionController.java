package com.thienan.product_service.core.stock.controller;

import com.thienan.product_service.core.stock.dto.StockOptionValueResponse;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.stock.dto.StockOptionRequest;
import com.thienan.product_service.core.stock.dto.StockOptionResponse;
import com.thienan.product_service.core.stock.dto.StockOptionValueRequest;
import com.thienan.product_service.core.stock.service.StockOptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thienan.product_service.core.stock.entity.StockOptionValue;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stock-options")
public class StockOptionController {
    private final StockOptionService service;

    @PostMapping
    public ResponseEntity<Long> create(
        @Valid
        @RequestBody
        StockOptionRequest stockOption) {
        return ResponseEntity.ok(service.createAndSave(stockOption));
    }

    @PatchMapping("/{id}/information")
    public ResponseEntity<Long> update(
        @PathVariable Long id,
        @Valid @RequestBody StockOptionRequest request) {
        return ResponseEntity.ok(service.updateInformation(id, request));
    }

    @PostMapping("/{id}/stock-option-values")
    public ResponseEntity<Long> addStockOptionValue(
        @PathVariable Long id,
        @Valid @RequestBody StockOptionValueRequest request) {
        return ResponseEntity.ok(service.addStockOptionValue(id, request));
    }

    @PatchMapping("/{id}/stock-option-values/{valueId}")
    public ResponseEntity<Long> updateStockOptionValue(
        @PathVariable Long id,
        @PathVariable Long valueId,
        @Valid @RequestBody StockOptionValueRequest request) {
        return ResponseEntity.ok(service.updateStockOptionValue(id, valueId, request));
    }

    @DeleteMapping("/{id}/stock-option-values/{valueId})")
    public ResponseEntity<Void> softDeleteStockOptionValue(
        @PathVariable Long id,
        @PathVariable Long valueId) {
        service.softDeleteStockOptionValue(id, valueId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/stock-option-values/{valueId}/hard-delete")
    public ResponseEntity<Void> deleteStockOptionValue(
        @PathVariable Long id,
        @PathVariable Long valueId) {
        service.hardDeleteStockOptionValue(id, valueId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOptionResponse> findById(
        @PathVariable Long id) {
        return ResponseEntity.ok(service.findDetailById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<StockOptionResponse>> findAll(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    } 

    @GetMapping("/search")
    public ResponseEntity<PageResponse<StockOptionResponse>> search(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String code) {
        return ResponseEntity.ok(service.search(pageable, name, code));
    }

    @GetMapping("/deleted")
    public ResponseEntity<PageResponse<StockOptionResponse>> findAllDeleted(
            @ParameterObject
            @PageableDefault(page=0, size=10)
            Pageable pageable) {
        return ResponseEntity.ok(service.findAllDeleted(pageable));
    }

    @GetMapping("{id}/stock-option-values/deleted")
    public ResponseEntity<PageResponse<StockOptionValueResponse>> findAllStockOptionValuesDeleted(
            @ParameterObject
            @PageableDefault(page=0, size=10)
            Pageable pageable) {
        return ResponseEntity.ok(service.findAllStockOptionValuesDeleted(pageable));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteById(
        @PathVariable Long id
    ){
        service.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/hard-delete")
    public ResponseEntity<Void> hardDeleteById(
        @PathVariable Long id
    ){
        service.hardDeleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}
