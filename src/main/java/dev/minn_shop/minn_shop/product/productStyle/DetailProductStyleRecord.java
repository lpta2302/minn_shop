package dev.minn_shop.minn_shop.product.productStyle;

import java.util.List;

import dev.minn_shop.minn_shop.product.productStyle.stock.Stock;

public record DetailProductStyleRecord(
        int id,
        int version,
        String name,
        String sku,
        byte[] coverImage,
        List<byte[]> images,
        List<Stock> stocks) {
}
