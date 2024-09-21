package dev.minn_shop.minn_shop.product.productStyle.stock;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockId implements Serializable {
    private Integer productStyleId;
    private Integer sizeId;

    public void addKey(Integer styleId, Integer sizeId){
        this.productStyleId = styleId;
        this.sizeId = sizeId;
    }
}
