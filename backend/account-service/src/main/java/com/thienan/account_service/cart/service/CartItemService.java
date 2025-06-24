package com.thienan.account_service.cart.service;

import org.springframework.stereotype.Service;

import com.thienan.account_service.cart.entity.CartItem;
import com.thienan.account_service.product.ProductAvailabilityResponse;
import com.thienan.account_service.product.StockClient;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final StockClient productVariantClient;


    public void checkProductAvailability(long productVariantId, long stockOptionValueId, long quantity){
        ProductAvailabilityResponse availabilityResponse = 
            productVariantClient.checkProductAvailability(productVariantId, stockOptionValueId, quantity);
        System.out.println(availabilityResponse);
        if (!availabilityResponse.available()) {
            throw new IllegalArgumentException(availabilityResponse.message());
        }
    }

    @Transactional
    public void updateItem(CartItem cartItem, CartItem existedItem){
        if (cartItem.getQuantity().equals(existedItem.getQuantity()) &&
            cartItem.getStockOptionValueId().equals(existedItem.getStockOptionValueId())
        ) {
            return;
        }    
        checkProductAvailability(cartItem.getProductVariantId(), cartItem.getStockOptionValueId() , cartItem.getQuantity());


        if (!cartItem.getQuantity().equals(existedItem.getQuantity())) {
            existedItem.setQuantity(cartItem.getQuantity());
        }

        if (!cartItem.getStockOptionValueId().equals(existedItem.getStockOptionValueId())) {
            existedItem.setStockOptionValueId(cartItem.getStockOptionValueId());
        }
    }
}
