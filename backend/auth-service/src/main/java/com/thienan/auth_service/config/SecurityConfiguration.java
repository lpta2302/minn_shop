package com.thienan.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.thienan.auth_service.security.SecurityFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
        "/auth/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html"
    };
    private final SecurityFilter securityFilter;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                    req.requestMatchers(WHITE_LIST_URL)
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // .authenticationProvider(authenticationProvider)
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            // .logout(logout ->
            //         logout.logoutUrl("/api/v1/auth/logout")
            //                 .addLogoutHandler(logoutHandler)
            //                 .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
            // )
        ;

        return http.build();
    }
}
