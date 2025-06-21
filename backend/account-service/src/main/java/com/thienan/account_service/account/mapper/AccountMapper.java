package com.thienan.account_service.account.mapper;

import org.springframework.stereotype.Component;
import com.thienan.account_service.account.dto.AccountDetail;
import com.thienan.account_service.account.entity.Account;

@Component
public class AccountMapper {
    // public AccountDetail convertToAccountDetail(Account account){
    //     return AccountDetail
    //         .builder()
    //         .email(account.getEmail())
    //         .password(account.getPassword())
    //         .fullname(account.getCustomer().getFullname())
    //         .build();
    // }
}
