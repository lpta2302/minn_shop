package com.thienan.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.thienan.gateway.auth.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
        "/api/v1/auth/**",
        "/api/v1/account-profiles/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception{
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange((request) -> 
                request.pathMatchers(WHITE_LIST_URL).permitAll()
                .anyExchange().authenticated()
            )
            // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // .authenticationProvider(null)
            .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
}
