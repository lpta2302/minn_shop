package com.thienan.account_service.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @Email(message="invalid email string")
    String email, 
    @Size(min=6, message="password at least 6 characters")
    String password,
    @Size(min=2, message="fullname at least 2 characters")
    String fullname
) {}
