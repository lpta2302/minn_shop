package com.thienan.product_service.core.product.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.product.dto.ProductInformationRequest;
import com.thienan.product_service.core.product.dto.ProductRequest;
import com.thienan.product_service.core.product.dto.ProductResponse;
import com.thienan.product_service.core.product.enums.ProductStatus;
import com.thienan.product_service.core.product.service.ProductService;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<Long> create(
        @Valid
        @RequestBody
        ProductRequest productRequest
    ){
        return ResponseEntity.ok(service.createAndSave(productRequest));
    }

    @PatchMapping("/{productId}/information")
    public ResponseEntity<Long> updateProductInformation(
        @Valid
        @RequestBody
        ProductInformationRequest productInformationRequest,
        @PathVariable
        Long productId
    ){
        return ResponseEntity.ok(service.updateProductInformation(productId ,productInformationRequest));
    }

    @PostMapping("/{productId}/product-variants")
    public ResponseEntity<Long> addProductVariant(
        @Valid
        @RequestBody
        ProductVariantRequest productVariantRequest,
        @PathVariable
        Long productId
    ){
        return ResponseEntity.ok(service.addProductVariant(productId, productVariantRequest));
    }

    @DeleteMapping("/{productId}/product-variants/{productVariantId}")
    public ResponseEntity<Long> removeProductVariant(
        @PathVariable
        Long productId,
        @PathVariable
        Long productVariantId
    ){
        return ResponseEntity.ok(service.removeProductVariant(productId, productVariantId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findWithVariantsById(
        @PathVariable Long productId) {
        return ResponseEntity.ok(service.findProductResponseWithVariantsById(productId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductResponse>> findAll(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<ProductResponse>> search(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String code,
        @RequestParam(required = false, name = "category-id") Long categoryId,
        @RequestParam(required = false, name = "category-code") String categoryCode,
        @RequestParam(required = false, name = "category-name") String categoryName,
        @RequestParam(required = false) ProductStatus status

        ) {
        return ResponseEntity.ok(service.search(
            pageable,
            name,
            code,
            categoryId,
            categoryCode,
            categoryName,
            status));
    }

    @GetMapping("/deleted")
    public ResponseEntity<PageResponse<ProductResponse>> findAllDeleted(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable) {
        return ResponseEntity.ok(service.findAllDeleted(pageable));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> softDeleteById(
        @PathVariable Long productId
    ){
        service.softDeleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}/hard-delete")
    public ResponseEntity<Void> hardDeleteById(
        @PathVariable Long productId
    ){
        service.hardDeleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productId}/recovery")
    public ResponseEntity<Long> recovery(
        @PathVariable Long productId
    ){
        return ResponseEntity.ok(service.recovery(productId));
    }

}
