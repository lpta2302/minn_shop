package com.thienan.product_service.core.stock.entity;

import com.thienan.product_service.common.BaseEntity;
import com.thienan.product_service.core.weight_type.entity.WeightType;

import jakarta.persistence.Entity;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "stock_option_values")
public class StockOptionValue extends  BaseEntity{
    @NotBlank(message = "name of stock option value can't be null or blank")
    @Size(max = 200, message = "name of stock option value length can't be more than 200 characters")
    private String name;

    @ManyToOne
    @JoinColumn(name="weight_type_id")
    private WeightType weightType;

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="stock_option_id")
    private StockOption stockOption;
}
