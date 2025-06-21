package com.thienan.auth_service.account;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    CUSTOMER, 
    ADMIN;

    public List<SimpleGrantedAuthority> grantedAuthorities(){
        return List.of(
            new SimpleGrantedAuthority(String.format("ROLE_%s", this.name()))
        );
    }
}
