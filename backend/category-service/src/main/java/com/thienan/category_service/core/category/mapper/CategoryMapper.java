package com.thienan.category_service.core.category.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thienan.category_service.core.category.dto.CategoryRequest;
import com.thienan.category_service.core.category.dto.CategoryResponse;
import com.thienan.category_service.core.category.entity.Category;

@Component
public class CategoryMapper {
    public Category convertToCategory(CategoryRequest request, Category parentCategory){
        return Category.builder()
            .code(request.code())
            .name(request.name())
            .parentCategory(parentCategory)
            .status(request.status())
            .build();
    }

    public Category convertToCategory(CategoryRequest request){
        return convertToCategory(request, null);
    }

    public CategoryResponse convertToCategoryResponse(
            Category category,
            List<Category> subCategories) {
        return CategoryResponse.builder()
            .id(category.getId())
            .code(category.getCode())
            .name(category.getName())
            .parentCategory(category.getParentCategory() == null ? null : category.getParentCategory().getId())
            .status(category.getStatus())
            .subCategories(
                subCategories == null ? new ArrayList<>() :
                subCategories.stream()
                    .map(subCategory -> convertToCategoryResponse(subCategory, null))
                    .toList()
            )
            .build();
    }

    public CategoryResponse convertToCategoryResponse(
        Category category
    ){
        return convertToCategoryResponse(category, category.getSubCategories());
    }
}
