package com.thienan.account_service.cart.entity;

import java.util.ArrayList;
import java.util.List;

import com.thienan.account_service.customer.entity.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private long version;

    @OneToOne(mappedBy="cart", fetch=FetchType.LAZY)
    private Customer customer;

    @Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    public void addCartItem(CartItem cartItem){
        items.add(cartItem);
        cartItem.setCart(this);
    }
}
