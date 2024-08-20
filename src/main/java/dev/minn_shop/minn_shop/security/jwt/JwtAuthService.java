package dev.minn_shop.minn_shop.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthService {
    private final SecretKey SECRET_KEY = SIG.HS256.key().build();
    private final int expirationTime = 3600 * 24 * 1000;

    private Claims extractClaims(String token){
        return Jwts
            .parser()
            .verifyWith(SECRET_KEY)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> solver){
        return solver.apply(extractClaims(token));
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isExpiredJwtToken(String token){
        return !extractExpiration(token).before(new Date());
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return Jwts
            .builder()
            .subject(userDetails.getUsername())
            .claims(claims)
            .signWith(SECRET_KEY)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationTime))
            .compact();
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    
}
