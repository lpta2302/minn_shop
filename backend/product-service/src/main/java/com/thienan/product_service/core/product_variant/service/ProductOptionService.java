package com.thienan.product_service.core.product_variant.service;

import com.thienan.product_service.core.product_variant.entity.ProductOption;
import com.thienan.product_service.core.product_variant.repository.ProductOptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.*;

@Service
@RequiredArgsConstructor
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    public List<ProductOption> findAllByName(List<String> names) {
        return productOptionRepository.findAllByNameIgnoreCaseIn(names);
    }

    public ProductOption findByName(String name){
        return productOptionRepository.findByNameIgnoreCase(name)
            .orElseThrow(()-> new EntityNotFoundException(format("Not found Product option with name %s", name)));
    }

    public ProductOption getReferenceByName(String productOptionName) {
        Long productOptionId = productOptionRepository.findIdByName(productOptionName)
            .orElse(null);

        if (productOptionId == null) {
            return null;
        }

        return productOptionRepository.getReferenceById(productOptionId);

    }
}
