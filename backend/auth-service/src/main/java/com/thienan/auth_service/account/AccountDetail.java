package com.thienan.auth_service.account;

public record  AccountDetail(
    String email,
    String password,
    String fullname
) {}
