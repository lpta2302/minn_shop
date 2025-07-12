package com.thienan.auth_service.authentication;

import com.thienan.auth_service.account.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @Email(message="invalid email string")
    String email, 
    @Size(min=6, message="password at least 6 characters")
    String password,
    @Size(min=2, max=200, message="first name must have 2 - 200 characters")
    String firstName,
    @Size(min=2, max=200, message="last name must have 2 - 200 characters")
    String lastName,
    @NotNull(message="Role can't be null")
    Role role
) {}
