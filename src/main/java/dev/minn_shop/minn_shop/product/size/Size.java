package dev.minn_shop.minn_shop.product.size;

import dev.minn_shop.minn_shop.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Size extends BaseEntity {
    @Column(unique = true)
    @NotNull(message = "Product's style description can't be null!")
    @NotBlank(message = "Product's style description can't be blank!")
    private String value;
}
