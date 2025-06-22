package com.thienan.auth_service.account;

import lombok.Builder;

@Builder
public record  AccountDetail(
    long id,
    String email,
    Role role,
    AccountStatus accountStatus
) {}
