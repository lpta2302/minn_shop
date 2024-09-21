package dev.minn_shop.minn_shop.product;

import java.util.List;

import dev.minn_shop.minn_shop.BaseEntity;
import dev.minn_shop.minn_shop.product.category.Category;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyle;
import dev.minn_shop.minn_shop.product.tag.Tag;
import jakarta.persistence.Basic;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
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
public class Product extends BaseEntity {
    @NotNull(message = "Product's name can't be null!")
    @NotBlank(message = "Product's name can't be blank!")
    private String name;

    @Column(unique = true)
    private String rootSku;

    private String description;

    @Column(columnDefinition = "float default 0")
    @Min(value = 0, message = "Price must be equal or greater than 0")
    @Builder.Default
    private float price = 0;

    @Lob
    @Basic(fetch = LAZY)
    private byte[] coverImage;

    // @Column(columnDefinition = "int default 0")
    // @Min(value = 0, message = "Quantity must be equal or greater than 0")
    // @Builder.Default
    // private int quantityInStock = 0;

    @Builder.Default
    private boolean is_active = true;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = { PERSIST, REMOVE })
    private List<ProductStyle> styles;

    @ManyToMany
    @JoinTable(name = "product_tag", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(name = "product_addStylescategory", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public void addStyles(List<ProductStyle> productStyles) {
        this.styles = productStyles;
        this.styles.forEach(style -> {
            style.setProduct(this);
            style.addStocks(style.getStocks());
        });
    }
}
