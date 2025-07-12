package com.thienan.order_service.order.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.order_service.common.PageResponse;
import com.thienan.order_service.order.dto.OrderRequest;
import com.thienan.order_service.order.entity.Order;
import com.thienan.order_service.order.enumeration.OrderStatus;
import com.thienan.order_service.order.service.OrderService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(
        @RequestBody OrderRequest request,
        @RequestHeader("userId")
        @Parameter(hidden=true)
        Long customerId
        ) {
            return ResponseEntity.ok(orderService.createOrder(customerId, request));
        }
        
    @GetMapping("/own")
    public ResponseEntity<PageResponse<Order>> getOwnOrders(
        @RequestHeader("userId")
        @Parameter(hidden=true)
        Long customerId,
        @ParameterObject
        @PageableDefault
        Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.getOwnOrders(customerId, pageable));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Long> changeOrderStatus(
        @PathVariable 
        Long orderId,
        OrderStatus orderStatus
    ){
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, orderStatus));
    }

    
}
