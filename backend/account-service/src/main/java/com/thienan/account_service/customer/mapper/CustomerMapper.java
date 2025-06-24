package com.thienan.account_service.customer.mapper;

import org.springframework.stereotype.Component;

import com.thienan.account_service.account.dto.AccountProfileRequest;
import com.thienan.account_service.customer.entity.Customer;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerMapper {
    public Customer convertToCustomer(AccountProfileRequest request){
        return Customer.builder()
            .id(request.account().getId())
            .firstName(request.firstName())
            .lastName(request.lastName())
            .account(request.account())
            .build();
    }
}
