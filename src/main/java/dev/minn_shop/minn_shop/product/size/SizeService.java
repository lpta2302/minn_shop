package dev.minn_shop.minn_shop.product.size;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeService {
    private final SizeRepository sizeRepository;

    public Size addSize(Size size) {
        return sizeRepository.save(size);
    }

    public List<Size> addSizes(List<Size> sizes) {
        return sizeRepository.saveAll(sizes);
    }

    public List<Size> addSizesByValues(List<String> sizeValues) {
        return addSizes(sizeValues.stream().map(
                value -> Size.builder().value(value).build()).collect(Collectors.toList()));
    }

    public List<Size> getSizeByValues(List<String> values) {
        return sizeRepository.findByValueIn(values);
    }

    public Map<String, Size> getSizeMap(List<String> values){
        return sizeRepository.findByValueIn(values).stream()
            .collect(Collectors.toMap(Size::getValue, Function.identity()));
    }
}
