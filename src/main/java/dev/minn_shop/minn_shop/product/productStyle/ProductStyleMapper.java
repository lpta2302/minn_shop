package dev.minn_shop.minn_shop.product.productStyle;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.product.productStyle.stock.Stock;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductStyleMapper {
    public ProductStyle coppyProductStyle(ProductStyle oldStyle, ProductStyle newStyle) {
        oldStyle.setName(newStyle.getName());
        oldStyle.setVersion(newStyle.getVersion());
        oldStyle.setSku(newStyle.getSku());
        oldStyle.setCoverImage(newStyle.getCoverImage());
        oldStyle.setImages(newStyle.getImages());
        oldStyle.addStocks(newStyle.getStocks());
        return oldStyle;
    }

    public ProductStyle toProductStyle(DetailProductStyleRecord record, List<Stock> stocks) {
        if (stocks == null) {
            stocks = record.stocks() == null ? new ArrayList<>() : record.stocks();
        }

        ProductStyle style = ProductStyle.builder()
                .id(record.id())
                .name(record.name())
                .sku(record.sku())
                .version(record.version())
                .coverImage(record.coverImage())
                .images(record.images())
                .build();

        style.addStocks(stocks);
        return style;
    }

    public ProductStyle toProductStyle(DetailProductStyleRecord record) {
        return toProductStyle(record, null);
    }

    public DetailProductStyleRecord toProductStyleRecord(ProductStyle productStyle) {
        return new DetailProductStyleRecord(
                productStyle.getId(),
                productStyle.getVersion(),
                productStyle.getName(),
                productStyle.getSku(),
                productStyle.getCoverImage(),
                productStyle.getImages(),
                productStyle.getStocks());
    }
}
