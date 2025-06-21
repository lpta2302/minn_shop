package com.thienan.gateway.auth;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.thienan.gateway.user.Account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter implements WebFilter {
    private final String AUTHORIZATION_HEADER = "Authorization";
    private final AuthClient authClient;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);
        final String jwt;

        if (exchange.getRequest().getPath().toString().startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
          return chain.filter(exchange);
        }
        
        jwt = authHeader.substring(7);

        return authClient.validateToken(jwt)
            .flatMap(validatingResponse -> {
                if (validatingResponse.isValid() && SecurityContextHolder.getContext().getAuthentication() == null) {
                    log.warn(validatingResponse.account().getEmail());
                    final Account userDetails = validatingResponse.account();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                    SecurityContext context = new SecurityContextImpl(authToken);
                    
                    // Set the Principal header in the request
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("userId", userDetails.getId().toString()) // Assuming username is appropriate for Principal
                        .header("email", userDetails.getEmail()) // Assuming username is appropriate for Principal
                        // .header("role", userDetails.getAuthorities()) // Assuming username is appropriate for Principal
                        .build();

                
                    // Create a new ServerWebExchange with the modified request
                    ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(modifiedRequest)
                        .build();
                
                    return chain.filter(modifiedExchange).contextWrite(
                        ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
                }
                return chain.filter(exchange);
            });
    }
    // private final AuthClient authClient;

    // @Override
    // protected void doFilterInternal(
    //     @NonNull HttpServletRequest request,
    //     @NonNull HttpServletResponse response,
    //     @NonNull FilterChain filterChain
    // ) throws IOException, ServletException{
    //     if (request.getServletPath().contains("/api/v1/auth")) {
    //         filterChain.doFilter(request, response);
    //         return;
    //     }

        
    //     final String authHeader = request.getHeader("Authorization");
    //     final String jwt;
    //     final UserDetails userDetails;
    //     if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
    //       filterChain.doFilter(request, response);
    //       return;
    //     }
        
    //     jwt = authHeader.substring(7);

    //     TokenValidatingResponse validatingResponse = authClient.validateToken(jwt);
    //     userDetails = validatingResponse.account();

    //     if (validatingResponse.isValid() && SecurityContextHolder.getContext().getAuthentication() == null) {
    //         UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
    //             userDetails,
    //             null,
    //             userDetails.getAuthorities()
    //         );
    //         authToken.setDetails(
    //             new WebAuthenticationDetailsSource().buildDetails(request)
    //         );
    //         SecurityContextHolder.getContext().setAuthentication(authToken);
    //     }
    //     filterChain.doFilter(request, response);
    // }
}
