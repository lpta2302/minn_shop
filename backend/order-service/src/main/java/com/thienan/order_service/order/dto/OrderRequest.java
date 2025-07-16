package com.thienan.order_service.order.dto;

import java.util.List;

import com.thienan.order_service.payment.PaymentMethod;

public record OrderRequest(
    String email,
    String phoneNumber,
    String firstName,
    String latName,
    String shippingAddress,
    PaymentMethod paymentMethod,
    List<OrderItemRequest> items
) {}
