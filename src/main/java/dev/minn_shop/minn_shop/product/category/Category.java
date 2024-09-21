package dev.minn_shop.minn_shop.product.category;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.minn_shop.minn_shop.BaseEntity;
import dev.minn_shop.minn_shop.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category extends BaseEntity {
    private String name;

    @ManyToMany(mappedBy="categories")
    @JsonIgnore
    private List<Product>products;

}
