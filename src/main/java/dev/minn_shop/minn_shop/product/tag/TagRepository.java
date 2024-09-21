package dev.minn_shop.minn_shop.product.tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer>{
    public Optional<Tag> findByName(String name);
    public List<Tag> findByNameIn(List<String> name);
}
