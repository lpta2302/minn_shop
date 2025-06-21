package com.thienan.auth_service.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.thienan.auth_service.account.Account;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Account account = Account.builder()
            .email(request.getHeader("email"))
            .build();
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                    account, null, null);

            SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
    
}
