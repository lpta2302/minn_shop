package com.thienan.gateway.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthClient {

    private final WebClient authWebClient;

    public Mono<TokenValidatingResponse> validateToken(String token) {
        return authWebClient
            .post()
            .uri("/validate-token")
            .header(HttpHeaders.AUTHORIZATION, token)
            .retrieve()
            .bodyToMono(TokenValidatingResponse.class);
    }
}
