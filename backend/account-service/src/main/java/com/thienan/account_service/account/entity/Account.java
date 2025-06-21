package com.thienan.account_service.account.entity;

import com.thienan.account_service.account.enumeration.AccountStatus;
import com.thienan.account_service.common.BaseEntity;
import com.thienan.account_service.customer.entity.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Email(message="invalid email string")
    private String email;

    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @OneToOne(mappedBy = "account", optional = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}
