package com.thienan.gateway.config;

import java.util.function.Consumer;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity.AuthorizeExchangeSpec;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationRules {
    public Consumer<AuthorizeExchangeSpec> allRules() {
        return exchanges -> {
            categoryEndpoints(exchanges);
            publicEndpoints(exchanges);
            exchanges.anyExchange().authenticated();
        };
    }

    public void publicEndpoints(AuthorizeExchangeSpec exchanges){
        exchanges
            .pathMatchers(SecurityConstants.PUBLIC_URL).permitAll();
    }

    public void categoryEndpoints(AuthorizeExchangeSpec exchanges){
        exchanges
            .pathMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll();
    }
}
