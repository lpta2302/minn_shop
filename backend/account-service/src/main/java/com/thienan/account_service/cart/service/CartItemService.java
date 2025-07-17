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


    public ProductAvailabilityResponse checkProductAvailability(long productVariantId, long stockOptionValueId, long quantity){
        ProductAvailabilityResponse availabilityResponse = 
            productVariantClient.checkProductAvailability(productVariantId, stockOptionValueId, quantity);

        if (!availabilityResponse.available()) {
            throw new IllegalArgumentException(availabilityResponse.message());
        } 

        return availabilityResponse;
    }

    @Transactional
    public void updateItem(CartItem cartItem, CartItem existedItem){
        if (cartItem.getQuantity().equals(existedItem.getQuantity()) &&
            cartItem.getStockOptionValue().id() == existedItem.getStockOptionValue().id()
        ) {
            return;
        }    
        var checkResponse = 
            checkProductAvailability(
                cartItem.getProductVariant().id(), 
                cartItem.getStockOptionValue().id(), cartItem.getQuantity());
        
        cartItem.setProductVariant(checkResponse.productVariant());
            
        if (!cartItem.getQuantity().equals(existedItem.getQuantity())) {
            existedItem.setQuantity(cartItem.getQuantity());
        }
        
        if (cartItem.getStockOptionValue().id() != existedItem.getStockOptionValue().id()) {
            existedItem.setStockOptionValue(cartItem.getStockOptionValue());                
            cartItem.setStockOptionValue(checkResponse.stockOptionValue());
        }

        cartItem.setAvailableQuantity(checkResponse.availableStock());
    }
}
