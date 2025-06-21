package com.thienan.auth_service.authentication;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thienan.auth_service.account.Account;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenValidatingResponse(
    boolean isValid,
    Account account,
    Map<String, String> extraClaims
) {}
