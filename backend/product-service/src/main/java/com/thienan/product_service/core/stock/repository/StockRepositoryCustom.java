package com.thienan.product_service.core.stock.repository;

import java.util.List;

import com.thienan.product_service.core.stock.dto.StockVariantOptionValuePair;
import com.thienan.product_service.core.stock.dto.StockWithVariantAndOptionValue;

public interface StockRepositoryCustom {
    List<StockWithVariantAndOptionValue> findAllByVariantOptionIds(List<StockVariantOptionValuePair> keys);
}
