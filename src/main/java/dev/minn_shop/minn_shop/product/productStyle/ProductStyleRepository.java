package dev.minn_shop.minn_shop.product.productStyle;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStyleRepository extends JpaRepository<ProductStyle, Integer> {
    public Optional<ProductStyle> findById(int id);

    public Optional<ProductStyle> findByNameLike(String name);

    public List<ProductStyle> findByNameIn(List<String> names);

    public List<ProductStyle> findByIdIn(List<Integer> id);
}
