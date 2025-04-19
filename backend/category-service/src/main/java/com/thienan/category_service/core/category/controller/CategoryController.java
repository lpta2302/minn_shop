package com.thienan.category_service.core.category.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thienan.category_service.common.PageResponse;
import com.thienan.category_service.core.category.dto.CategoryRequest;
import com.thienan.category_service.core.category.dto.CategoryResponse;
import com.thienan.category_service.core.category.entity.Category;
import com.thienan.category_service.core.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RequestMapping("/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.createAndSave(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(
        @PathVariable Long id,
        @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateAndSave(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @GetMapping("/{id}/full-detail")
    public ResponseEntity<CategoryResponse> findByIdWithFullDetail(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findByIdWithFullDetail(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> findAll(
            @PageableDefault(page = 0, size = 10) @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @GetMapping("/is-displayed")
    public ResponseEntity<PageResponse<CategoryResponse>> findAllDisplayedByIdWithFullDetail(
        @PageableDefault(page = 0, size = 10) 
        @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAllDisplayWithFullDetail(pageable));
    }

    @GetMapping("/{parentCategoryId}")
    public ResponseEntity<PageResponse<CategoryResponse>> findAllSubCategories(
            @PageableDefault(page = 0, size = 10) @ParameterObject Pageable pageable,
            @PathVariable Long parentCategoryId) {
        return ResponseEntity.ok(categoryService.findAllSubCategories(pageable, parentCategoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<CategoryResponse>> search(
            @PageableDefault(page = 0, size = 10) @ParameterObject Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code) {
        return ResponseEntity.ok(categoryService.search(pageable, name, code));
    }

    @PatchMapping("/{id}/soft-delete")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        categoryService.softDeleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

}
