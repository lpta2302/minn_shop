package com.thienan.category_service.core.category.dto;

import java.util.List;

import com.thienan.category_service.core.category.entity.CategoryStatus;

import lombok.Builder;

@Builder
public record CategoryResponse(
    Long id,
    String code,
    String name,
    Long parentCategory,
    CategoryStatus status,
    List<CategoryResponse> subCategories
) {}
