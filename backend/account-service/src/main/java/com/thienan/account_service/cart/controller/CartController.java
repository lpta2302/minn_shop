package com.thienan.account_service.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.account_service.cart.entity.CartItem;
import com.thienan.account_service.cart.service.CartService;
import com.thienan.account_service.handler.exceptions.common.UnauthorizedException;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PatchMapping("/items/{cartItemId}")
    @SecurityRequirement(name="${application.config.open-api.security.bearer-token}")
    public ResponseEntity<Long> updateItem(
        @PathVariable 
        Long cartItemId,
        @RequestBody @Valid CartItem cartItem,
        @RequestHeader("userId") 
        @Parameter(hidden=true)
        Long userId
    ) {
        if (userId == null) {
            throw new UnauthorizedException();
        }
        return ResponseEntity.ok(
            cartService.updateItem(userId, cartItem, cartItemId)
        );
    }

    @PatchMapping("/items")
    @SecurityRequirement(name="${application.config.open-api.security.bearer-token}")
    public ResponseEntity<Long> addItem(
        @RequestBody @Valid CartItem cartItem,
        @RequestHeader("userId")
        @Parameter(hidden=true)
        Long userId
    ) {
        if (userId == null) {
            throw new UnauthorizedException();
        }
        return ResponseEntity.ok(
            cartService.addItem(userId, cartItem)
        );
    }
    
}
