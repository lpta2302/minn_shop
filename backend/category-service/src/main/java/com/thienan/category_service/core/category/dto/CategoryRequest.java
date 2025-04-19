package com.thienan.category_service.core.category.dto;

import com.thienan.category_service.core.category.entity.CategoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @Size(max = 100, message = "code length can't be more than 100 characters")
    String code,
    @NotBlank(message = "category name can't be null or blank")
    @Size(max = 200, message = "category name length can't be more than 200 characters")
    String name,
    Long parentCategoryId,
    CategoryStatus status
) {}
