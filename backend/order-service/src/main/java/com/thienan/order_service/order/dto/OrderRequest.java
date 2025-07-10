package com.thienan.order_service.order.dto;

import java.util.List;

import com.thienan.order_service.payment.PaymentMethod;

public record OrderRequest(
    String shippingAddress,
    PaymentMethod paymentMethod,
    List<OrderItemRequest> items
) {}
