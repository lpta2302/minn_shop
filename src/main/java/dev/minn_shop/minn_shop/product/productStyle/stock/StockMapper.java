package dev.minn_shop.minn_shop.product.productStyle.stock;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockMapper {
    private final StockRepository stockRepository;

    public Stock toStock(StockRecord record){
        Stock stock = stockRepository.findBySizeAndStyleId(record.size(),
        record.styleId())
        .orElseThrow(()->new EntityNotFoundException(
        "Not found stock for style: "+record.styleId()+"have size: "+record.size()
        ));

        stock.setQuantityInStock(record.quantityInStock());

        return stock;
    }
}
