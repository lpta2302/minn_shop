package com.thienan.product_service.core.weight_type.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.thienan.product_service.common.PageResponse;
import com.thienan.product_service.core.weight_type.entity.WeightType;
import com.thienan.product_service.core.weight_type.service.WeightTypeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weight-types")
public class WeightTypeController {
    private final WeightTypeService service;

    @PostMapping
    public ResponseEntity<Long> create(
        @RequestBody 
        @Valid 
        WeightType weightType) {
        return ResponseEntity.ok(service.createAndSave(weightType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(
        @PathVariable Long id, 
        @RequestBody @Valid 
        WeightType weightType) {
        return ResponseEntity.ok(service.updateAndSave(id, weightType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeightType> findById(
        @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<WeightType>> findAll(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAll(pageable));
    } 

    @GetMapping("/deleted")
    public ResponseEntity<PageResponse<WeightType>> findAllDeleted(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable
    ) {
        return ResponseEntity.ok(service.findAllDeleted(pageable));
    } 

    @GetMapping("/search")
    public ResponseEntity<PageResponse<WeightType>> search(
        @ParameterObject
        @PageableDefault(page=0, size=10)
        Pageable pageable,
        String name,
        String code,
        Integer minWeight,
        Integer maxWeight) {
        return ResponseEntity.ok(service.search(pageable, name, code, minWeight, maxWeight));
    }
    
    @PatchMapping("/{id}/recovery")
    public ResponseEntity<Long> recovery(
        @PathVariable Long id
    ){
        return ResponseEntity.ok(service.recovery(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> softDeleteById(
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
