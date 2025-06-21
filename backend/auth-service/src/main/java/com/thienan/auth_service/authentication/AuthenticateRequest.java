package com.thienan.auth_service.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AuthenticateRequest(
    @Email(message="invalid email string")
    String email, 
    @Size(min=6, message="password at least 6 characters")
    String password
) {}
