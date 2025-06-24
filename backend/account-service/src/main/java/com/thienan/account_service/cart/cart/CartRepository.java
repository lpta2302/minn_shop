package com.thienan.account_service.cart.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thienan.account_service.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

    Optional<Cart> findByCustomer_Id(long customerId);
    
}
