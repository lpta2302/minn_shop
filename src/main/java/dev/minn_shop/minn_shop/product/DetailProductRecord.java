package dev.minn_shop.minn_shop.product;

import java.util.List;

import dev.minn_shop.minn_shop.product.productStyle.DetailProductStyleRecord;

public record DetailProductRecord(
                int id,
                String name,
                String rootSku,
                String description,
                float price,
                byte[] coverImage,
                List<DetailProductStyleRecord> styles,
                List<String> categories,
                List<String> tags) {
}
