package com.thienan.auth_service.token;

import org.springframework.stereotype.Service;

import com.thienan.auth_service.account.Account;

import jakarta.persistence.EntityNotFoundException;
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

    public boolean validateToken(String jwtToken) {
        return tokenRepository.findByToken(jwtToken)
            .map(token -> !token.isExpired() && !token.isRevoked())
            .orElseThrow(()-> new EntityNotFoundException(
                String.format("Not found token: %s", jwtToken)));
    }
}
