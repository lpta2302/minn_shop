package com.thienan.order_service.product;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.thienan.order_service.order.dto.OrderItemRequest;
import com.thienan.order_service.order.dto.OrderItemsAvailabilityResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(
    name="${application.openFeign.stock-service.name}",
    url="${application.openFeign.stock-service.url}"
)
public interface StockClient {
    @PutMapping("/reserve")
    OrderItemsAvailabilityResponse checkOrderItemAvailability(
        @RequestBody List<OrderItemRequest> items
    );

    @PostMapping("/with-brief-detail")
    List<Stock> findStocksWithBriefDetail(List<OrderItemRequest> items);

    @PutMapping("/deduct")
    OrderItemsAvailabilityResponse deductStock(List<OrderItemRequest> itemRequests);
}
