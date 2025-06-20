package com.thienan.auth_service.token;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.thienan.auth_service.account.Account;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    @Value("${application.security.secretKey}")
    private String secretKey;

    @Value("${application.security.jwt-expiration}")
    private long jwtExpiration;

    @Value("${application.security.refresh-expiration}")
    private long refreshTokenExpiration;
    
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    
    public String generateToken(
        Map<String, String> extraClaims,
        UserDetails userDetails){
            return buildToken(extraClaims, userDetails, jwtExpiration);
        }

    public String generateRefreshToken(Account newAccount) {
        return generateRefreshToken(new HashMap<>(), newAccount);
    }

    public String generateRefreshToken(Map<String, String> extraClaims, Account newAccount) {
        return buildToken(extraClaims, newAccount, refreshTokenExpiration);
    }

    private String buildToken(
        Map<String, String> extraClaims, 
        UserDetails userDetails,
        long expiration) {
        return Jwts.builder()
            .signWith(getSignInKey())
            .subject(userDetails.getUsername())
            .claims(extraClaims)
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .issuedAt(new Date(System.currentTimeMillis()))
            .compact();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
