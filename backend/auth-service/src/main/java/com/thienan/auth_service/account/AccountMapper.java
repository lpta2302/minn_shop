package com.thienan.auth_service.account;

import org.springframework.stereotype.Service;

@Service
public class AccountMapper {
    public AccountDetail convertToAccountDetail(Account account){
        return AccountDetail.builder()
            .id(account.getId())
            .email(account.getEmail())
            .role(account.getRole())
            .accountStatus(account.getStatus())
            .build();
    }
}
