package dev.minn_shop.minn_shop.product.size;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
    public Optional<Size> findByValueLike(String value);

    public List<Size> findByValueIn(List<String> values);
}
