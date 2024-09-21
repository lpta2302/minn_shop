package dev.minn_shop.minn_shop.product;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.minn_shop.minn_shop.product.productStyle.ProductStyle;
import dev.minn_shop.minn_shop.product.productStyle.ProductStyleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {
    private final ProductService productService;
    private final ProductStyleService productStyleService;
    /*
     * search by:
     * - name: contain
     * - sku: contain
     * - price: between
     * - type: equal
     * - tag: equal
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProductsPages(
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String sku,
            @RequestParam(required = false, name = "price-min") Integer priceMin,
            @RequestParam(required = false, name = "price-max") Integer priceMax,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "page-size") int pageSize) {
        return ResponseEntity.ok(productService
                .getProductsPages(
                        tags, category, name, sku, priceMin, priceMax, page, pageSize));
    }

    @PostMapping
    public ResponseEntity<DetailProductRecord> addProduct(@RequestBody DetailProductRecord product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PatchMapping("/products/{id}/product-styles")
    public ResponseEntity<ProductStyle> updateProductStyle(@RequestBody ProductStyle newStyle) {
        return  ResponseEntity.ok(productStyleService.updateProductStyle(newStyle));
    }
    
}
