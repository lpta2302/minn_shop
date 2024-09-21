package dev.minn_shop.minn_shop.product.category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.product.DetailProductRecord;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> saveNewCategories(DetailProductRecord productRecord) {
                Map<String, Category> categories = new HashMap<>();

                categoryRepository.findByNameIn(productRecord.categories())
                                .forEach(category -> categories.put(category.getName(), category));

                Set<Category> unsavedCategories = new HashSet<>();

                productRecord.categories().stream()
                                .forEach(categoryName -> {
                                        if (!categories.containsKey(categoryName)) {
                                                unsavedCategories.add(Category.builder().name(categoryName).build());
                                        }
                                });

                List<Category> savedCategories = categoryRepository
                                .saveAll(unsavedCategories.stream().collect(Collectors.toList()));
                savedCategories.addAll(categories.values());

                return savedCategories;
        }
}
