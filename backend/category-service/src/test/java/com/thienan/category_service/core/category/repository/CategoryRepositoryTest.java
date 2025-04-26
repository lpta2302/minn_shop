package com.thienan.category_service.core.category.repository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thienan.category_service.core.category.entity.Category;
import static com.thienan.category_service.core.category.entity.CategoryStatus.ACTIVE;

import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
     @Autowired
    private CategoryRepository categoryRepository;

    private Category parentCategory;
    private Category childCategory;

    @BeforeEach
    public void setUp() {
        // Create a parent category (or you could use a test fixture)
        parentCategory = new Category();
        parentCategory.setCode("PARENT1");
        parentCategory.setName("Parent Category");
        parentCategory.setStatus(ACTIVE);
        categoryRepository.save(parentCategory);

        childCategory = new Category();
        childCategory.setCode("CHILD1");
        childCategory.setName("Child Category");
        childCategory.setStatus(ACTIVE);
        childCategory.setParentCategory(parentCategory);
        categoryRepository.save(childCategory);

        parentCategory.addCategory(childCategory);
        categoryRepository.save(parentCategory);
    }


    @Test
    void testFindDetailById() {
        Optional<Category> foundCategory = categoryRepository.findWithSubCategoriesById(childCategory.getId());

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCode()).isEqualTo("CHILD1");
        assertThat(foundCategory.get().getParentCategory()).isEqualTo(parentCategory);
    }

    @Test
    void testFindDetailParentById() {
        Optional<Category> foundCategory = categoryRepository.findWithSubCategoriesById(parentCategory.getId());

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCode()).isEqualTo("PARENT1");
        assertThat(foundCategory.get().getSubCategories().size()).isEqualTo(1);
        assertThat(foundCategory.get().getSubCategories().get(0)).isEqualTo(childCategory);
    }

    @Test
    void deleteChildren(){
        categoryRepository.delete(childCategory);
        parentCategory.getSubCategories().remove(childCategory);
        categoryRepository.save(parentCategory);

        Optional<Category> foundCategory = categoryRepository.findWithSubCategoriesById(parentCategory.getId());

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCode()).isEqualTo("PARENT1");
        assertThat(foundCategory.get().getSubCategories().size()).isEqualTo(0);
    }
}
