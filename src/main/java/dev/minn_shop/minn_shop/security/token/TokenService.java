package dev.minn_shop.minn_shop.security.token;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public boolean isValidToken(String tokenString) {
        final Token token = tokenRepository.findByToken(tokenString)
                .orElseThrow(() -> new RuntimeException("Token not found!"));

        return !token.isExpired() &&
                !token.isRevoked();
    }
}
