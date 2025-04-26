package com.thienan.category_service.core.category.service;

import static java.lang.String.format;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thienan.category_service.common.PageResponse;
import com.thienan.category_service.core.category.dto.CategoryRequest;
import com.thienan.category_service.core.category.dto.CategoryResponse;
import com.thienan.category_service.core.category.entity.Category;
import com.thienan.category_service.core.category.mapper.CategoryMapper;
import com.thienan.category_service.core.category.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createAndSave(CategoryRequest categoryRequest) {
        var newCategory = categoryMapper.convertToCategory(categoryRequest);
        var savedCategory = categoryRepository.save(newCategory);

        if (categoryRequest.parentCategoryId() != null) {
            var parentCategory = findById(categoryRequest.parentCategoryId());
            parentCategory.addCategory(savedCategory);
            categoryRepository.save(parentCategory);
        }
        
        return savedCategory.getId();
    }

    @Transactional
    public Long updateAndSave(Long categoryId, CategoryRequest categoryRequest) {
        Category updatingCategory = findById(categoryId);
        if (!updatingCategory.getCode().equals(categoryRequest.code())) {
            updatingCategory.setCode(categoryRequest.code());
        }

        if (!updatingCategory.getName().equals(categoryRequest.name())) {
            updatingCategory.setName(categoryRequest.name());
        }

        if (!updatingCategory.getStatus().equals(categoryRequest.status())){
            updatingCategory.setStatus(categoryRequest.status());
        }

        var oldParentCategory = updatingCategory.getParentCategory();
        Long newParentCategoryId = categoryRequest.parentCategoryId();

        if (updatingCategory.getParentCategory() == null){
            if (newParentCategoryId != null){
                var parentCategory = findById(newParentCategoryId);
                parentCategory.addCategory(updatingCategory);
                updatingCategory.setParentCategory(parentCategory);
            }
        } else if (newParentCategoryId == null){
            updatingCategory.getParentCategory().getSubCategories().remove(updatingCategory);
            categoryRepository.save(updatingCategory.getParentCategory());
            updatingCategory.setParentCategory(null);
        } else if(!oldParentCategory.getId().equals(newParentCategoryId)){
            var parentCategory = findById(newParentCategoryId);
            parentCategory.addCategory(updatingCategory);
            oldParentCategory.getSubCategories().remove(updatingCategory);
            categoryRepository.save(oldParentCategory);
            categoryRepository.save(parentCategory);
        }

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

    public PageResponse<CategoryResponse> findAllDeleted(Pageable pageable){
        var pageResult = categoryRepository.findAllDeleted(pageable);
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

    public void softDeleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Long recoveryById(Long id) {
        return categoryRepository.recoveryById(id).getId();
    }

    @Transactional
    public void hardDeleteById(Long id) {
        categoryRepository.hardDeleteById(id);
    }
}