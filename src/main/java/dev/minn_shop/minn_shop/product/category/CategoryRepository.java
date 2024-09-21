package dev.minn_shop.minn_shop.product.category;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public List<Category> findByNameIn(List<String> name);
}
