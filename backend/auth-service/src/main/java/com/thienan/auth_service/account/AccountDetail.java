package com.thienan.auth_service.account;

import lombok.Builder;

@Builder
public record  AccountDetail(
    String email,
    String fullname,
    Role role,
    AccountStatus accountStatus
) {}
