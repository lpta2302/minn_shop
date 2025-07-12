package com.thienan.order_service.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name="${application.openFeign.customer-service.name}",
    url="${application.openFeign.customer-service.url}"
)
public interface CustomerClient {
    @GetMapping("/{customerId}")
    Customer findCustomerById(@PathVariable long customerId);
}
