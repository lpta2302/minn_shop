package dev.minn_shop.minn_shop.product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.minn_shop.minn_shop.product.tag.Tag;

public interface ProductRepository extends JpaRepository<Product, Integer>  {
    public Optional<List<Product>> findByName(String name);

    @Query("""
    select p 
    from Product p
    Join p.tags t
    where t in :tags
            """)
    public List<Product> findByTag(@Param("tags") List<Tag> tags);

    @Query("""
            select p
            from Product p
            where
            (:name is null or p.name like concat('%', :name , '%')) and
            (:rootSku is null or p.rootSku like concat('%', :rootSku , '%')) and
            (:price_min is null or p.price >= :price_min) and
            (:price_max is null or p.price <= :price_max) and
            (:category is null or 
                exists (select 1 from p.categories c where c.name = :category)) and
            (:tags is null or 
                exists (select 1 from p.tags t where t.name in :tags))
            """)
    public Page<Product> getAllFilteredProducts(
        List<String> tags,
        String category,
        String name,
        String rootSku,
        @Param("price_min") Integer priceMin,
        @Param("price_max") Integer priceMax,
        Pageable pageable);

}
