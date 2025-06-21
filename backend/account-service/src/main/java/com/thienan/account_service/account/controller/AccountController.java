package com.thienan.account_service.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.account_service.account.dto.AccountDetail;
import com.thienan.account_service.account.dto.RegisterRequest;
import com.thienan.account_service.account.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<String> register(
        @Valid
        @RequestBody
        RegisterRequest request
    ){
        return ResponseEntity.ok(accountService.registerAccount(request));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AccountDetail> findAccountByEmail(
        @PathVariable
        String email
    ) {
        return ResponseEntity.ok(accountService.findAccountByEmail(email));
    }
    
}
