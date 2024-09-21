package dev.minn_shop.minn_shop.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.product.category.Category;
import dev.minn_shop.minn_shop.product.category.CategoryRepository;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyle;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyleMapper;
import dev.minn_shop.minn_shop.product.tag.Tag;
import dev.minn_shop.minn_shop.product.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductMapper {
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final ProductStyleMapper productStyleMapper;

    public DetailProductRecord toDetailProductRecord(Product product) {
        return new DetailProductRecord(
                product.getId(),
                product.getName(),
                product.getRootSku(),
                product.getDescription(),
                product.getPrice(),
                product.getCoverImage(),
                product.getStyles().stream().map(style->
                    productStyleMapper.toProductStyleRecord(style)).toList(),
                product.getCategories().stream().map(Category::getName).collect(Collectors.toList()),
                product.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
    }

    public Product toProduct(DetailProductRecord record, List<Tag> tags, List<ProductStyle> styles,
            List<Category> categories) {
        if (tags == null) {
            tags = tagRepository.findByNameIn(record.tags());
        }

        if (styles == null) {
            styles = record.styles() == null ? 
            new ArrayList<>() :
            record.styles().stream().map(styleRecord-> 
                productStyleMapper.toProductStyle(styleRecord)).toList();
            }

        if (categories == null) {
            categories = categoryRepository.findByNameIn(record.categories());
        }

        Product product = Product.builder()
                .name(record.name())
                .price(record.price())
                .rootSku(record.rootSku())
                .description(record.description())
                .coverImage(record.coverImage())
                .tags(tags)
                .categories(categories)
                .build();

        product.addStyles(styles);
        return product;
    }

    public Product toProduct(DetailProductRecord record) {
        List<Tag> tags = tagRepository.findByNameIn(record.tags());
        List<Category> categories = categoryRepository.findByNameIn(record.categories());

        return toProduct(record, tags, new ArrayList<>(), categories);
    }
}
