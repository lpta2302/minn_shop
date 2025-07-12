package com.thienan.auth_service.authentication;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    String accessToken,
    String refreshToken
) {}
