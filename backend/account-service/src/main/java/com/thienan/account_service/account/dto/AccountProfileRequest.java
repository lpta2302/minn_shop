package com.thienan.account_service.account.dto;

import com.thienan.account_service.account.entity.Account;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountProfileRequest(
    @NotNull
    Account account,
    @NotNull
    @Size(min=2, max=200, message="first name must have 2 - 200 characters")
    String firstName,
    @NotNull
    @Size(min=2, max=200, message="last name must have 2 - 200 characters")
    String lastName
) {}
