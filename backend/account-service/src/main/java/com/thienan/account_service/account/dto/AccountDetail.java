package com.thienan.account_service.account.dto;

public record AccountDetail(
    String email,

    String password,
    
    String fullname
) {}
