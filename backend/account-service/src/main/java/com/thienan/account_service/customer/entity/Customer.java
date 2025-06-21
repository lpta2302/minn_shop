package com.thienan.account_service.customer.entity;

import static jakarta.persistence.CascadeType.ALL;
import java.time.LocalDate;
import com.thienan.account_service.account.entity.Account;
import com.thienan.account_service.cart.entity.Cart;
import com.thienan.account_service.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Size(min = 2, message = "Fullname must have at least 2 characters")
    private String fullname;
    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "phone number string has invalid character")
    private String phoneNumber;
    private String shippingAddress;
    private LocalDate dateOfBirth;

    @OneToOne(optional = true)
    private Account account;

    @Default
    @OneToOne(cascade = ALL)
    private Cart cart = new Cart();
}
