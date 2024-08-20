package dev.minn_shop.minn_shop.security.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.minn_shop.minn_shop.security.jwt.JwtAuthService;
import dev.minn_shop.minn_shop.security.token.Token;
import dev.minn_shop.minn_shop.security.token.TokenRepository;
import dev.minn_shop.minn_shop.security.token.TokenType;
import dev.minn_shop.minn_shop.user.RoleRepository;
import dev.minn_shop.minn_shop.user.RoleType;
import dev.minn_shop.minn_shop.user.User;
import dev.minn_shop.minn_shop.user.UserRepository;
import dev.minn_shop.minn_shop.user.customer.Customer;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtAuthService jwtAuthService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        User user = null;
        System.out.println(request);
        if (request.getRole().equals(RoleType.CUSTOMER.toString())) {
            user = Customer
                    .builder()
                    .roles(Collections.of(
                            roleRepository.findByName(RoleType.CUSTOMER)
                                    .orElseThrow(() -> new RuntimeException(request.getRole() + " not found!"))))
                    .build();
        }

        if (user == null) {
            throw new RuntimeException("Failed to register a customer");
        }

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(user);

        Token token = Token.builder()
                .token(jwtAuthService.generateToken(user))
                .type(TokenType.BEARER)
                .user(user)
                .build();

        tokenRepository.save(token);

        return new AuthResponse(token.getToken());
    }

    public AuthResponse authenticate(AuthenticateRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Token token = Token.builder()
                .token(jwtAuthService.generateToken(user))
                .type(TokenType.BEARER)
                .user(user)
                .build();

        tokenRepository.save(token);

        return new AuthResponse(token.getToken());
    }
}
