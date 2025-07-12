package com.thienan.account_service.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name="${application.openFeign.stock-service.name}",
    url="${application.openFeign.stock-service.url}"
)
public interface StockClient {
    @GetMapping("{productVariantId}/{stockOptionValueId}/available")
    ProductAvailabilityResponse checkProductAvailability(
        @PathVariable
        long productVariantId,
        @PathVariable
        long stockOptionValueId,
        @RequestParam
        long quantity
    );
}
