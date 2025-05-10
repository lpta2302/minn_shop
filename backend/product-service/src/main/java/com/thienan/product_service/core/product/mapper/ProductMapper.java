package com.thienan.product_service.core.product.mapper;

import com.thienan.product_service.core.product.dto.ProductRequest;
import com.thienan.product_service.core.product.dto.ProductResponse;
import com.thienan.product_service.core.product.entity.Product;
import com.thienan.product_service.core.product_variant.dto.ProductVariantResponse;
import com.thienan.product_service.core.product_variant.entity.ProductVariant;
import com.thienan.product_service.core.product_variant.mapper.ProductVariantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductVariantMapper productVariantMapper;

    public Product newProductWithInformation(ProductRequest productRequest) {
        return Product
            .builder()
            .code(productRequest.code())
            .name(productRequest.name())
            .description(productRequest.description())
            .status(productRequest.status())
            .build();
    }

    public ProductResponse convertToProductResponse(Product product) {
        List<ProductVariantResponse> productVariants = product.getProductVariants() == null ?
            new ArrayList<>() :
            product.getProductVariants().stream().map(
                productVariant -> productVariantMapper.convertToProductVariantResponse(
                    productVariant, null
                )
            ).toList();
        return ProductResponse
            .builder()
            .id(product.getId())
            .code(product.getCode())
            .name(product.getName())
            .category(product.getCategory())
            .description(product.getDescription())
            .productVariants(productVariants)
            .status(product.getStatus())
            .build();
    }
}
