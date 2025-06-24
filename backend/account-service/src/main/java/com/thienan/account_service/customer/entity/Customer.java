package com.thienan.account_service.customer.entity;

import java.time.LocalDate;

import com.thienan.account_service.account.entity.Account;
import com.thienan.account_service.cart.entity.Cart;
import com.thienan.account_service.common.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import static jakarta.persistence.CascadeType.ALL;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Override
    @Column(name = "id") 
    public Long getId() {
        return super.getId(); 
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Size(min=2, max=200, message="first name must have 2 - 200 characters")
    String firstName;
    @Size(min=2, max=200, message="last name must have 2 - 200 characters")
    String lastName;
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "phone number string has invalid character")
    private String phoneNumber;
    private String shippingAddress;
    private LocalDate dateOfBirth;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "account_id")),
        @AttributeOverride(name = "email", column = @Column(name = "email")),
        @AttributeOverride(name = "role", column = @Column(name = "account_role")),
    })
    private Account account;

    @Default
    @OneToOne(cascade = ALL)
    private Cart cart = new Cart();
}
