package com.thienan.product_service.core.product_variant.controller;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;
import com.thienan.product_service.core.product_variant.dto.ProductVariantResponse;
import com.thienan.product_service.core.product_variant.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-variants")
public class ProductVariantController {
    private final ProductVariantService productVariantService;

    @PatchMapping("/{variantId}")
    public ResponseEntity<Long> updateInformation(
        @PathVariable Long variantId,
        @Valid @RequestBody ProductVariantRequest productVariantRequest
    ){
        return ResponseEntity.ok(productVariantService.updateProductVariant(variantId, productVariantRequest));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductVariantResponse>> findAll(
        @ParameterObject
        @PageableDefault
        Pageable pageable
    ){
        return ResponseEntity.ok(productVariantService.findAll(pageable));
    }
    
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<PageResponse<ProductVariantResponse>> findAllByCategory(
        @ParameterObject
        @PageableDefault
        Pageable pageable,
        @PathVariable
        Long categoryId
    ){
        return ResponseEntity.ok(productVariantService.findAllByCategory(pageable, categoryId));
    }
}
