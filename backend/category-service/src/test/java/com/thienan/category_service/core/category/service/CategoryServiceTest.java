package com.thienan.category_service.core.category.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.thienan.category_service.core.category.dto.CategoryRequest;
import com.thienan.category_service.core.category.entity.Category;
import static com.thienan.category_service.core.category.entity.CategoryStatus.ACTIVE;
import com.thienan.category_service.core.category.mapper.CategoryMapper;
import com.thienan.category_service.core.category.repository.CategoryRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryMapper mockMapper;

    private Category parentCategory;
    private Category childCategory;

    @BeforeEach
    public void setUp() {
        childCategory = new Category();
        childCategory.setId(2L);
        childCategory.setCode("CAT2");
        childCategory.setName("Example Category 2");
        childCategory.setStatus(ACTIVE);
        childCategory.setParentCategory(parentCategory);

        parentCategory = new Category();
        parentCategory.setId(1L);
        parentCategory.setCode("CAT1");
        parentCategory.setName("Example Category");
        parentCategory.setStatus(ACTIVE);
        parentCategory.setSubCategories(List.of(childCategory));
    }

    @Test
    void testUpdateAndSave_shouldUpdateNewParentAndReturnId() {
        // given
        Long categoryId = 2L;
        Long parentId = parentCategory.getId();

        Category existing = new Category();
        existing.setId(categoryId);
        existing.setCode("OLD_CODE");
        existing.setName("Old Name");
        existing.setParentCategory(null);

        CategoryRequest request = new CategoryRequest("NEW_CODE", "New Name", parentId, ACTIVE);

        Category updatedCategory = categoryMapper.convertToCategory(request, parentCategory);
        updatedCategory.setId(categoryId);
        // when
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existing));
        Mockito.when(categoryRepository.findById(parentId)).thenReturn(Optional.of(parentCategory));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(updatedCategory);

        // then
        Long result = categoryService.updateAndSave(categoryId, request);

        assertEquals(categoryId, result);

        // verify behavior
        verify(categoryRepository).save(Mockito.argThat(category -> 
            category.getCode().equals("NEW_CODE") &&
            category.getName().equals("New Name") &&
            category.getParentCategory().getId().equals(parentId)
        ));
    }

    @Test
    void testUpdateAndSave_shouldUpdateNullParentAndReturnId() {
        // given
        Long categoryId = 2L;
        Long parentId = parentCategory.getId();

        Category existing = new Category();
        existing.setId(categoryId);
        existing.setCode("OLD_CODE");
        existing.setName("Old Name");
        existing.setParentCategory(parentCategory);

        CategoryRequest request = new CategoryRequest("NEW_CODE", "New Name", null, ACTIVE);

        Category updatedCategory = categoryMapper.convertToCategory(request, parentCategory);
        updatedCategory.setId(categoryId);

        // when
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existing));
        Mockito.when(categoryRepository.findById(parentId)).thenReturn(Optional.of(parentCategory));
        Mockito.when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(updatedCategory);

        // then
        Long result = categoryService.updateAndSave(categoryId, request);

        assertEquals(categoryId, result);

        // verify behavior
        verify(categoryRepository).save(Mockito.argThat(category -> 
            category.getCode().equals("NEW_CODE") &&
            category.getName().equals("New Name") &&
            category.getParentCategory() == null
        ));
    }

    @Test
    void testFindAllDisplayWithFullDetail() {
        // given
        var pageable = PageRequest.of(0, 10);
        var parentResponse = categoryMapper.convertToCategoryResponse(parentCategory);
        var childResponse = categoryMapper.convertToCategoryResponse(childCategory);

        // when
        Mockito.when(categoryRepository.findAllDisplayWithFullDetail(pageable)).thenReturn(new PageImpl<>(List.of(parentCategory, childCategory)));
        Mockito.when(mockMapper.convertToCategoryResponse(parentCategory)).thenReturn(parentResponse);
        Mockito.when(mockMapper.convertToCategoryResponse(childCategory)).thenReturn(childResponse);
        // then
        var responses = categoryService.findAllDisplayWithFullDetail(PageRequest.of(0, 10));
        
        // verify
        assertEquals(responses.totalElements(), 2);
        assertEquals(responses.content().get(0), parentResponse);
        assertEquals(responses.content().get(1), childResponse);
        assertEquals(responses.content().get(0).subCategories().size(), 1);
    }
}
