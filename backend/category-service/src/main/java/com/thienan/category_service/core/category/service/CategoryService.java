package com.thienan.category_service.core.category.service;

import static java.lang.String.format;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.thienan.category_service.common.PageResponse;
import com.thienan.category_service.core.category.dto.CategoryRequest;
import com.thienan.category_service.core.category.dto.CategoryResponse;
import com.thienan.category_service.core.category.entity.Category;
import com.thienan.category_service.core.category.entity.CategoryStatus;
import com.thienan.category_service.core.category.mapper.CategoryMapper;
import com.thienan.category_service.core.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    public Category createFrom(CategoryRequest request) {
        if (request.parentCategoryId() != null) {
            Category parentCategory = findById(request.parentCategoryId());
            return categoryMapper.convertToCategory(request, parentCategory);
        } else {
            return categoryMapper.convertToCategory(request, null);
        }
    }

    public Long createAndSave(CategoryRequest categoryRequest) {
        return categoryRepository.save(createFrom(categoryRequest)).getId();
    }

    public Long updateAndSave(Long categoryId, CategoryRequest categoryRequest) {
        Category updatingCategory = findById(categoryId);
        if (!updatingCategory.getCode().equals(categoryRequest.code())) {
            updatingCategory.setCode(categoryRequest.code());
        }

        if (!updatingCategory.getName().equals(categoryRequest.name())) {
            updatingCategory.setName(categoryRequest.name());
        }

        if (categoryRequest.parentCategoryId() == null) {
            updatingCategory.setParentCategory(null);
        } else if (updatingCategory.getParentCategory() == null ||
            !updatingCategory.getParentCategory().getId().equals(categoryRequest.parentCategoryId())) {
                Category foundCategory = findById(categoryRequest.parentCategoryId());
                updatingCategory.setParentCategory(foundCategory);
        }

        updatingCategory.setStatus(categoryRequest.status());

        return categoryRepository.save(updatingCategory).getId();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(format("not found entity with id: %d", id)));
    }

    public CategoryResponse findByIdWithFullDetail(Long id) {
        Category category = categoryRepository.findWithSubCategoriesById(id).orElseThrow(
                () -> new EntityNotFoundException(format("not found entity with id: %d", id)));
        return categoryMapper.convertToCategoryResponse(category);
    }

    public PageResponse<CategoryResponse> findAllDisplayWithFullDetail(Pageable pageable){
        var pageResult = categoryRepository.findAllDisplayWithFullDetail(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream().map(
                categoryMapper::convertToCategoryResponse
            ).toList()
        );
    }

    public PageResponse<CategoryResponse> findAll(Pageable pageable){
        var pageResult = categoryRepository.findAll(pageable);
        return PageResponse.fromPage(
            pageResult,
            pageResult.stream().map(
                categoryMapper::convertToCategoryResponse
            ).toList()
        );
    }

    public PageResponse<CategoryResponse> search(
        Pageable pageable,
        String code, 
        String name){
            var pageResult = categoryRepository.search(pageable, code, name);
            return PageResponse.fromPage(
                pageResult,
                pageResult.stream().map(
                    categoryMapper::convertToCategoryResponse
                ).toList()
            );
    }

    public PageResponse<CategoryResponse> findAllSubCategories(Pageable pageable, Long parentId){
        var pageResult = categoryRepository.findAllSubCategories(pageable, parentId);
        return PageResponse.fromPage(pageResult, 
            pageResult.stream().map(categoryMapper::convertToCategoryResponse).toList());
    }

    public Long softDeleteCategoryById(Long id) {
        var category = findById(id);
        category.setStatus(CategoryStatus.DELETED);
        return categoryRepository.save(category).getId();
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
