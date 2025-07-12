package com.thienan.auth_service.authentication;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @Valid
        @RequestBody
        RegisterRequest registerRequest
    ){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticateRequest authenticateRequest
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<TokenValidatingResponse> validateToken(
        @RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(authenticationService.validateToken(authHeader));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(
        HttpServletRequest request,
        HttpServletResponse response) 
        throws StreamWriteException, DatabindException, IOException {
        return ResponseEntity.ok(authenticationService.refreshToken(request, response));
    }
    
    @GetMapping
    public String getMethodName() {
        return "Hello";
    }
    
    
}
