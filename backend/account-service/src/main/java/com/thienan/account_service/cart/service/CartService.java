package com.thienan.account_service.cart.service;

import org.springframework.stereotype.Service;

import com.thienan.account_service.cart.cart.CartRepository;
import com.thienan.account_service.cart.entity.Cart;
import com.thienan.account_service.cart.entity.CartItem;
import com.thienan.account_service.handler.exceptions.common.EntityNotFoundByIDException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;


    public void validateCartItem(CartItem cartItem, Cart cart){
        boolean isExisted = cart.getItems()
            .stream()
            .anyMatch(item-> 
                item.getProductVariantId().equals(cartItem.getProductVariantId()) &&
                item.getStockOptionValueId().equals(cartItem.getStockOptionValueId()));
        if (isExisted) {
            throw new IllegalArgumentException("Existed cart item but not have id in path variable");
        }

        cartItemService.checkProductAvailability(cartItem.getProductVariantId(), cartItem.getStockOptionValueId(), cartItem.getQuantity());
    }

    public Long updateItem(long customerId ,CartItem cartItem, Long cartItemId){
        Cart cart = findByCustomerId(customerId);

        CartItem existedItem = cart
            .getItems()
            .stream().filter(item -> item.getId().equals(cartItemId))
            .findFirst()
            .orElseThrow(()-> new EntityNotFoundByIDException("Cart item", cartItemId.toString()));
        cartItemService.updateItem(cartItem, existedItem);        
        cartRepository.save(cart);

        return cart.getId();
    }

    public Long addItem(Long customerId, CartItem cartItem) {
        Cart cart = findByCustomerId(customerId);
        validateCartItem(cartItem, cart);
        cart.addCartItem(cartItem);
        
        cartRepository.save(cart);

        return cart.getId();    
    }

    public Cart findByCustomerId(long customerId){
        var cart = cartRepository.findByCustomer_Id(customerId)
            .orElseThrow(()-> new EntityNotFoundException(
                String.format("Not found cart for customer have id: %s", customerId)
            ));

        return cart;
    }

}
