package com.thienan.order_service.customer;

import jakarta.persistence.Embeddable;

@Embeddable
public record Customer(
    Long id,
    String email,
    String fullName,
    String phoneNumber
) {}
