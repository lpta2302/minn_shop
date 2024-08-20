package dev.minn_shop.minn_shop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.security.token.Token;
import dev.minn_shop.minn_shop.security.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String header  = request.getHeader("Authorization");
        final String jwt;

        if(header == null || !header.startsWith("Bearer ")){
            System.out.println("header not found" + header);
            return;
        }

        jwt = header.substring(7);

        Token token = tokenRepository.findByToken(jwt).orElseThrow(()->new RuntimeException("Token not found!"));
        token.setExpired(true);
        token.setRevoked(true);

        tokenRepository.save(token);
    }

}
