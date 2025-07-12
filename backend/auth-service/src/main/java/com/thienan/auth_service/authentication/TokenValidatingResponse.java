package com.thienan.auth_service.authentication;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thienan.auth_service.account.AccountDetail;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TokenValidatingResponse(
    boolean isValid,
    AccountDetail account,
    Map<String, String> extraClaims
) {}
