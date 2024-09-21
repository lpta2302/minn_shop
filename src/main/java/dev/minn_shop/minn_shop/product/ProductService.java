package dev.minn_shop.minn_shop.product;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.product.category.Category;
import dev.minn_shop.minn_shop.product.category.CategoryService;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyle;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyleMapper;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyleService;
import dev.minn_shop.minn_shop.product.size.SizeService;
import dev.minn_shop.minn_shop.product.tag.Tag;
import dev.minn_shop.minn_shop.product.tag.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
        private final ProductRepository productRepository;
        private final SizeService sizeService;
        private final ProductMapper productMapper;
        private final ProductStyleMapper productStyleMapper;
        /*
         * search by:
         * - name: contain
         * - sku: contain
         * - price: between
         * - type: equal
         * - tag: equal
         */
        private final TagService tagService;
        private final CategoryService categoryService;
        private final ProductStyleService productStyleService;

        public List<Product> getProductsPages(
                        List<String> tags,
                        String category,
                        String name,
                        String sku,
                        Integer priceMin,
                        Integer priceMax,
                        int page,
                        int pageSize) {

                return productRepository.getAllFilteredProducts(
                                tags, category, name, sku, priceMin, priceMax,
                                PageRequest.of(page, pageSize)).toList();

        }

        public DetailProductRecord addProduct(DetailProductRecord productRecord) {
                // save new tags and categories
                List<Tag> tags = tagService.saveNewTags(productRecord);
                List<Category> categories = categoryService.saveNewCategories(productRecord);

                // save styles
                List<ProductStyle> styles = productStyleService.createProductStyles(productRecord.styles());

                return productMapper.toDetailProductRecord(
                                productRepository.save(
                                                productMapper.toProduct(productRecord, tags, styles, categories)));
        }

        public Product updateProduct(int id, DetailProductRecord record) {
                Product product = productRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException("Not found product with id: " + id));

                product.setName(record.name());
                product.setRootSku(record.rootSku());
                product.setDescription(record.description());
                product.setPrice(record.price());
                product.setCoverImage(record.coverImage());

                return productRepository.save(product);
        }

}