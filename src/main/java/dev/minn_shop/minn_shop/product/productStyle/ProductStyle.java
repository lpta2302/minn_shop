package dev.minn_shop.minn_shop.product.productStyle;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.minn_shop.minn_shop.BaseEntity;
import dev.minn_shop.minn_shop.product.Product;
import dev.minn_shop.minn_shop.product.productStyle.stock.Stock;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductStyle extends BaseEntity {
    @NotNull(message = "Product's style description can't be null!")
    @NotBlank(message = "Product's style description can't be blank!")
    private String name;

    @Column(unique = true)
    private String sku;

    @Lob
    private byte[] coverImage;

    @Lob
    @ElementCollection(fetch = LAZY)
    private List<byte[]> images;

    @ManyToOne
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "productStyle", cascade = CascadeType.ALL)
    private List<Stock> stocks;

    public void addStocks(List<Stock> stocks) {
        this.stocks = stocks;
        this.stocks.forEach(stock -> {
            stock.setProductStyle(this);
            stock.addStock(this, stock.getSize(), stock.getQuantityInStock());
        });
    }
    // public void setStocks(List<Stock> stocks, List<Size> sizes) {
    // this.stocks = stocks;
    // this.stocks.forEach(stock -> stock.addStock(this, null,
    // stock.getQuantityInStock()));
    // }
}
