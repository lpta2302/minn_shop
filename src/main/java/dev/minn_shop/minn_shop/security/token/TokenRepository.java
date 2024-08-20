package dev.minn_shop.minn_shop.security.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer>{
    public Optional<Token> findByToken(String token);    
}
