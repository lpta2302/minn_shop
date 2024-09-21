package dev.minn_shop.minn_shop.product.productStyle.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.minn_shop.minn_shop.product.productStyle.ProductStyle;
import dev.minn_shop.minn_shop.product.size.Size;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
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
public class Stock {
    @EmbeddedId
    @Builder.Default
    private StockId id = new StockId();

    @Version
    private Integer version;

    @ManyToOne
    @MapsId("productStyleId")
    @JoinColumn(name = "product_style_id")
    @JsonIgnore
    private ProductStyle productStyle;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "size_id")
    private Size size;

    @Builder.Default
    @Min(value = 0, message = "Quantity can't be negative")
    private int quantityInStock = 0;

    @Builder.Default
    private boolean isActive = true;

    public void addStock(ProductStyle style, Size size, int quantityInStock) {
        this.productStyle = style;
        this.size = size;
        this.quantityInStock = quantityInStock;
    }
}
