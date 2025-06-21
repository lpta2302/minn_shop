package com.thienan.account_service.account.service;

import org.springframework.stereotype.Service;

import com.thienan.account_service.account.dto.AccountDetail;
import com.thienan.account_service.account.dto.RegisterRequest;
import com.thienan.account_service.account.entity.Account;
import com.thienan.account_service.account.repository.AccountRepository;
import com.thienan.account_service.customer.entity.Customer;
import com.thienan.account_service.customer.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final CustomerService customerService;

    @Transactional
    public String registerAccount(RegisterRequest request){
        Customer newCustomer = customerService.createAndSave(
            Customer.builder().fullname(request.fullname()).build()
        );

        Account account = Account
            .builder()
            .email(request.email())
            .password(request.password())
            .build();

        accountRepository.save(account);
        newCustomer.setAccount(account);

        return null;
    }

    public AccountDetail findAccountByEmail(String email){
        var account = accountRepository.findByEmail(email)
            .orElseThrow(()->new EntityNotFoundException(
                String.format("Not found account with email: %s", email)
            ));
        return account;
    }
}
