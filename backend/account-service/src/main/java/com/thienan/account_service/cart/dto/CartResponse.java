package com.thienan.account_service.cart.dto;

import java.util.List;

import com.thienan.account_service.cart.entity.CartItem;

import lombok.Builder;

@Builder
public record CartResponse(
    int totalItem,
    List<CartItem> items
) {}
