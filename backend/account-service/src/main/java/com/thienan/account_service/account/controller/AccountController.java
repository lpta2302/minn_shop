package com.thienan.account_service.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thienan.account_service.account.dto.AccountProfileRequest;
import com.thienan.account_service.account.service.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/account-profiles")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Long> create(
        @Valid
        @RequestBody
        AccountProfileRequest request
    ){
        return ResponseEntity.ok(accountService.create(request));
    }

    // @GetMapping("/email/{email}")
    // public ResponseEntity<AccountDetail> findAccountByEmail(
    //     @PathVariable
    //     String email
    // ) {
    //     return ResponseEntity.ok(accountService.findAccountByEmail(email));
    // }
    
}
