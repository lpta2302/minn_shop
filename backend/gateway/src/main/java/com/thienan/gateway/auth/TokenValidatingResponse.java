package com.thienan.gateway.auth;

import com.thienan.gateway.user.Account;


public record TokenValidatingResponse(
    boolean isValid,
    Account account
) {}
