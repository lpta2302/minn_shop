package com.thienan.category_service.core.category.repository;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.thienan.category_service.core.category.entity.Category;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTest {
     @Autowired
    private CategoryRepository categoryRepository;

    private Category parentCategory;

    @BeforeEach
    public void setUp() {
        // Create a parent category (or you could use a test fixture)
        parentCategory = new Category();
        parentCategory.setCode("PARENT1");
        parentCategory.setName("Parent Category");
        categoryRepository.save(parentCategory);
    }

    @Test
    void testFindDetailById() {
        Category category = new Category();
        category.setCode("CHILD1");
        category.setName("Child Category");
        category.setParentCategory(parentCategory);
        categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findWithSubCategoriesById(category.getId());

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCode()).isEqualTo("CHILD1");
        assertThat(foundCategory.get().getParentCategory()).isEqualTo(parentCategory);
    }

    @Test
    void testFindDetailParentById() {
        Category category = new Category();
        category.setCode("CHILD1");
        category.setName("Child Category");
        category.setParentCategory(parentCategory);
        categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findWithSubCategoriesById(parentCategory.getId());

        assertThat(foundCategory).isPresent();
        assertThat(foundCategory.get().getCode()).isEqualTo("PARENT1");
        assertThat(foundCategory.get().getSubCategories().size()).isEqualTo(1);
        assertThat(foundCategory.get().getSubCategories().get(0)).isEqualTo(category);
    }
}
