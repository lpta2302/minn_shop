package com.thienan.auth_service.token;

import org.springframework.stereotype.Service;

import com.thienan.auth_service.account.Account;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public Token createToken(Account user, String jwtToken){
        var token = Token.builder()
            .token(jwtToken)
            .account(user)
            .isExpired(false)
            .isRevoked(false)
            .build();
        return tokenRepository.save(token);
    }
}
