package com.thienan.account_service.customer.dto;

import lombok.Builder;

@Builder
public record CustomerDetail(
    Long id,
    String email,
    String fullName,
    String phoneNumber,
    String shippingAddress
) {}
