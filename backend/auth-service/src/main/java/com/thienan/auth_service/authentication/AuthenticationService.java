package com.thienan.auth_service.authentication;

import org.springframework.stereotype.Service;

import com.thienan.auth_service.account.AccountService;
import com.thienan.auth_service.token.JwtTokenService;
import com.thienan.auth_service.token.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;

    public AuthenticationResponse register(RegisterRequest registerRequest){
        var newAccount = accountService.registerAccount(registerRequest);
        var jwtToken = jwtTokenService.generateToken(newAccount);
        var refreshToken = jwtTokenService.generateRefreshToken(newAccount);

        tokenService.createToken(newAccount, jwtToken);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }
}
