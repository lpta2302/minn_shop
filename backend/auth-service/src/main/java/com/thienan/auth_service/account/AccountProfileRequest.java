package com.thienan.auth_service.account;

import lombok.Builder;

@Builder
public record AccountProfileRequest(
    AccountDetail account,
    String firstName,
    String lastName
) {}
