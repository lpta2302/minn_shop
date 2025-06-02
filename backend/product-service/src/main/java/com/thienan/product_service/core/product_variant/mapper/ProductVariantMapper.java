package com.thienan.product_service.core.product_variant.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.thienan.product_service.core.product_variant.dto.ProductOptionResponse;
import com.thienan.product_service.core.product_variant.dto.ProductVariantRequest;
import com.thienan.product_service.core.product_variant.dto.ProductVariantResponse;
import com.thienan.product_service.core.product_variant.entity.ProductVariant;
import com.thienan.product_service.core.stock.dto.StockResponse;
import com.thienan.product_service.core.stock.entity.Stock;
import com.thienan.product_service.core.stock.mapper.StockMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductVariantMapper {

    private final StockMapper stockMapper;

    public ProductVariant convertProductVariant(ProductVariantRequest productVariantRequest, List<Stock> stocks) {
        return ProductVariant
            .builder()
            .variantId(productVariantRequest.variantId())
            .name(productVariantRequest.name())
            .price(productVariantRequest.price())
            .discount(productVariantRequest.discount())
            .originalPrice(productVariantRequest.originalPrice())
            .slug(productVariantRequest.slug())
            .status(
                productVariantRequest.status()
            )
            .stocks(stocks)
            .build();
    }

    public ProductVariant convertProductVariant(ProductVariantRequest productVariantRequest){
        return convertProductVariant(productVariantRequest, null);
    }

    public ProductVariantResponse convertToProductVariantResponse(ProductVariant productVariant, List<StockResponse> stockResponses) {
        return ProductVariantResponse
            .builder()
            .id(productVariant.getId())
            .variantId(productVariant.getVariantId())
            .name(productVariant.getName())
            .price(productVariant.getPrice())
            .originalPrice(productVariant.getOriginalPrice())
            .discount(productVariant.getDiscount())
            .slug(productVariant.getSlug())
            .soldQuantity(productVariant.getSoldQuantity())
            .productOption(ProductOptionResponse
                .builder()
                .id(productVariant.getProductOption().getId())
                .name(productVariant.getProductOption().getName())
                .build())
            .status(productVariant.getStatus())
            .stocks(stockResponses)
            .build();
    }

    public ProductVariantResponse convertToProductVariantResponse(ProductVariant productVariant) {
        List<StockResponse> stocks = productVariant.getStocks().stream().map(
            stockMapper::convertToStockResponse
        ).toList();
        return convertToProductVariantResponse(productVariant, stocks);
    }

    public ProductVariant copyToProductVariant(ProductVariant oldProductVariant) {
        return ProductVariant
            .builder()
            .id(oldProductVariant.getId())
            .version(oldProductVariant.getVersion())
            .variantId(oldProductVariant.getVariantId())
            .name(oldProductVariant.getName())
            .slug(oldProductVariant.getSlug())
            .price(oldProductVariant.getPrice())
            .discount(oldProductVariant.getDiscount())
            .originalPrice(oldProductVariant.getOriginalPrice())
            .status(oldProductVariant.getStatus())
            .productOption(oldProductVariant.getProductOption())
            .product(oldProductVariant.getProduct())
            .build();
    }
}
