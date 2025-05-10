package com.thienan.product_service.core.product_variant.entity;

import com.thienan.product_service.common.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_options")
public class ProductOption extends BaseEntity{
    @NotBlank(message = "name of product variant option can't be null or blank")
    @Size(max = 200, message = "name of product variant option length can't be more than 200 characters")
    @Column(unique = true)
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductOption productOption){
            return Objects.equals(productOption.getName(), this.getName());
        }

        return super.equals(obj);

    }
}
