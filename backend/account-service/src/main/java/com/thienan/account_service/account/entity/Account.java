package com.thienan.account_service.account.entity;

import com.thienan.account_service.account.enumeration.Role;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Account {
    private Long id;
    private String email;
    private Role role;
}
