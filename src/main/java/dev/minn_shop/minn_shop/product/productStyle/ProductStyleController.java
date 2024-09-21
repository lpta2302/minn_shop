package dev.minn_shop.minn_shop.product.productStyle;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/product-styles")
public class ProductStyleController {
    private final ProductStyleService productStyleService;
    @GetMapping("/")
    public ResponseEntity<ProductStyle> getProductStyleById(@RequestParam int id) {
        return ResponseEntity.ok(productStyleService.getProductStyleById(id));
    }

    public ResponseEntity<ProductStyle> updateProductStyle(@RequestBody ProductStyle productStyle){
        return ResponseEntity.ok(productStyleService.updateProductStyle(productStyle));
    }
    
    public ResponseEntity<List<ProductStyle>> updateProductStyle(@RequestBody List<ProductStyle> styles){
        return ResponseEntity.ok(styles);
    }
}
