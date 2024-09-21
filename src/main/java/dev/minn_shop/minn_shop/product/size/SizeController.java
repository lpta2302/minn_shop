package dev.minn_shop.minn_shop.product.size;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/sizes")
public class SizeController {
    private final SizeService sizeService;

    @PostMapping
    public ResponseEntity<Size> createNewSize(@RequestBody Size sizes) {        
        return ResponseEntity.ok(sizeService.addSize(sizes));
    }
    
}
