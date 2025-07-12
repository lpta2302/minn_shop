package com.thienan.account_service.account.service;

import org.springframework.stereotype.Service;

import com.thienan.account_service.account.dto.AccountProfileRequest;
import com.thienan.account_service.account.enumeration.Role;
import com.thienan.account_service.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CustomerService customerService;
    
    public Long create(AccountProfileRequest request) {
        if (request.account().getRole().equals(Role.CUSTOMER)) {
            return customerService.createAndSave(request); 
        }

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
    
}
