package com.thienan.product_service.core.category;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${application.config.category-service.name}", url = "${application.config.category-service.url}")
public interface CategoryClient {
    @GetMapping("/{id}")
    Category findById(@PathVariable Long id);
}
