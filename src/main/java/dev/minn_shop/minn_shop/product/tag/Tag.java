package dev.minn_shop.minn_shop.product.tag;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.minn_shop.minn_shop.BaseEntity;
import dev.minn_shop.minn_shop.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Tag extends BaseEntity {
    @Column(unique = true)
    @NotBlank(message = "name can't be blank")
    @NotNull(message = "name can't be null")
    private String name;

    @ManyToMany(mappedBy="tags")
    @JsonIgnore
    private List<Product> products;
}
