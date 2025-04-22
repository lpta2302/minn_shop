package com.thienan.product_service.core.weight_type.entity;

import com.thienan.product_service.common.BaseEntity;
import com.thienan.product_service.core.weight_type.enums.WeightTypeStatus;

import jakarta.persistence.Entity;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weight_types")
public class WeightType extends BaseEntity{
    @Size(max = 100, message = "product code length can't be more than 100 characters")
    private String code;
    
    @NotBlank(message = "name of weight type can't be null or blank")
    @Size(max = 200, message = "name of weight type length can't be more than 200 characters")
    private String name;

    @PositiveOrZero(message="min must be positive or zero")
    private int min;

    @PositiveOrZero(message="max must be positive or zero")
    private int max;

    @Enumerated(STRING)
    private WeightTypeStatus status;
}
