package com.thienan.auth_service.authentication;

import org.springframework.stereotype.Service;

import com.thienan.auth_service.account.Account;
import com.thienan.auth_service.account.AccountService;
import com.thienan.auth_service.handler.exceptions.common.BadRequestException;
import com.thienan.auth_service.token.JwtTokenService;
import com.thienan.auth_service.token.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenService tokenService;
    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;

    public AuthenticationResponse register(RegisterRequest registerRequest){
        var newAccount = accountService.registerAccount(registerRequest);
        var jwtToken = jwtTokenService.generateToken(newAccount);
        var refreshToken = jwtTokenService.generateRefreshToken(newAccount);

        tokenService.createToken(newAccount, jwtToken);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticateRequest loginRequest){
        var user = accountService.findByEmail(loginRequest.email());
        var jwtToken = jwtTokenService.generateToken(user);
        var refreshToken = jwtTokenService.generateRefreshToken(user);

        // revokeAllUserTokens(user);
        tokenService.createToken(user, jwtToken);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }

    public TokenValidatingResponse validateToken(String jwtToken){
        var userEmail = jwtTokenService.extractUsername(jwtToken);

        if (userEmail == null) {
            throw new BadRequestException("Token doesn't have username");
        }

        Account account = accountService.findByEmail(userEmail);
        var isTokenValid = tokenService.validateToken(jwtToken);

        if (jwtTokenService.isTokenValid(jwtToken, account) && isTokenValid) {
          return TokenValidatingResponse.builder()
            .isValid(isTokenValid)
            .account(account)
            .build();
          
        //     UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
        //       userDetails,
        //       null,
        //       userDetails.getAuthorities()
        //   );
        //   authToken.setDetails(
        //       new WebAuthenticationDetailsSource().buildDetails(request)
        //   );
        //   SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        return TokenValidatingResponse.builder().isValid(false).build();
    }
}
