package com.thienan.account_service.customer.dto;

import java.time.LocalDate;

import com.thienan.account_service.account.entity.Account;

public record CustomerRequest(
    String fullname,
    LocalDate dateOfBirth,
    Account account
) {}
