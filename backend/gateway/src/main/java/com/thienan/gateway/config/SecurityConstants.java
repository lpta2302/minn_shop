package com.thienan.gateway.config;

public class SecurityConstants {
    public static final String[] PUBLIC_URL = {
        "/api/v1/auth/**",
        "/api/v1/account-profiles/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html"
    };
}
