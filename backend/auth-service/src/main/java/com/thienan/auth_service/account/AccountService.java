package com.thienan.auth_service.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.thienan.auth_service.authentication.RegisterRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
    
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final AccountProfileClient accountProfileClient;


    @Transactional
    public Account registerAccount(RegisterRequest registerRequest){
        var isExistedEmail = accountRepository.existsByEmail(registerRequest.email());

        if (isExistedEmail) {
            throw new IllegalArgumentException("Existed email");
        }

        var newAccount = Account.builder()
            .password(passwordEncoder.encode(registerRequest.password()))
            .email(registerRequest.email())
            .role(registerRequest.role())
            .status(AccountStatus.INACTIVE)
            .build();

        var savedAccount = accountRepository.save(newAccount);
        var accountRequest = AccountProfileRequest.builder()
            .account(accountMapper.convertToAccountDetail(newAccount))
            .firstName(registerRequest.firstName())
            .lastName(registerRequest.lastName())
            .build();

        accountProfileClient.createProfile(accountRequest);

        return savedAccount;
    }

    public Account findByEmail(String email){
        var account = accountRepository.findByEmail(email)
            .orElseThrow(()->new EntityNotFoundException(
                String.format("Not found account with email: %s", email)
            ));
        return account;
    }

}
