package dev.minn_shop.minn_shop.product.productStyle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.product.size.Size;
import dev.minn_shop.minn_shop.product.size.SizeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductStyleService {
    private final ProductStyleRepository productStyleRepository;
    private final ProductStyleMapper productStyleMapper;
    private final SizeService sizeService;

    public List<ProductStyle> createProductStyles(List<DetailProductStyleRecord> styleRecords){
        List<String> sizeValues = styleRecords.stream()
            .flatMap(record->record.stocks().stream())
            .map(stock->stock.getSize().getValue()).toList();

        Map<String, Size> sizes = sizeService.getSizeMap(sizeValues);

        List<ProductStyle> styles = styleRecords.stream().map(record->{
            record.stocks().forEach(stock->stock.setSize(sizes.get(stock.getSize().getValue())));
            return productStyleMapper.toProductStyle(record);
        }).toList();

        return List.of(productStyleRepository.save(styles.get(0)));
        // return productStyleRepository.saveAll(styles);
    }

    public ProductStyle getProductStyleById(int id) {
        return productStyleRepository.findById(id)
            .orElseThrow(()->new EntityNotFoundException("Not found Product style with id: "+id));
    }
    public ProductStyle updateProductStyle(ProductStyle productStyle) {
        ProductStyle savedProductStyle = productStyleRepository.findById(productStyle.getId())
            .orElseThrow(()->new EntityNotFoundException("Not found product style with id: " + productStyle.getId()));
        productStyleMapper.coppyProductStyle(savedProductStyle, productStyle);
        return productStyleRepository.save(savedProductStyle);
    }

}
