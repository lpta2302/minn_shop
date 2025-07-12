package com.thienan.auth_service.token;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.thienan.auth_service.account.Account;

import io.jsonwebtoken.Claims;
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
            .signWith(getSigninKey())
            .subject(userDetails.getUsername())
            .claims(extraClaims)
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .issuedAt(new Date(System.currentTimeMillis()))
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Claims extractAllClaims(String jwtToken){
        return Jwts.parser()
            .verifyWith(getSigninKey())
            .build()
            .parseSignedClaims(jwtToken)
            .getPayload();
    }
     
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSigninKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
