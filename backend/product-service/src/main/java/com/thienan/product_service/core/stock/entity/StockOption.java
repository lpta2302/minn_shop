package com.thienan.product_service.core.stock.entity;

import java.util.ArrayList;
import java.util.List;

import com.thienan.product_service.common.BaseEntity;
import com.thienan.product_service.core.stock.enums.StockOptionStatus;

import static jakarta.persistence.CascadeType.ALL;
import jakarta.persistence.Entity;
import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "stock_options")
public class StockOption extends BaseEntity{
    @Size(max = 100, message = "length of stock option code can't be more than 100 characters")
    private String code;

    @NotBlank(message = "name of stock option can't be null or blank")
    @Size(max = 200, message = "name of stock option length can't be more than 200 characters")
    private String name;

    @OneToMany(mappedBy="stockOption", orphanRemoval=true, cascade=ALL)
    private List<StockOptionValue> stockOptionValues;

    @Enumerated(STRING)
    private StockOptionStatus status;

    public void setStockOptionValues(List<StockOptionValue> stockOptionValues){
        if (this.stockOptionValues == null) {
            this.stockOptionValues = new ArrayList<>();
        } else {
            this.stockOptionValues.clear();
        }

        stockOptionValues.forEach((stockOptionValue) -> {
            stockOptionValue.setStockOption(this);
            this.stockOptionValues.add(stockOptionValue);
        });
    }

    public void addStockOptionValue(StockOptionValue value) {
        if (this.stockOptionValues == null) {
            this.stockOptionValues = new ArrayList<>();
        }

        value.setStockOption(this);
        this.stockOptionValues.add(value);
    }
}
