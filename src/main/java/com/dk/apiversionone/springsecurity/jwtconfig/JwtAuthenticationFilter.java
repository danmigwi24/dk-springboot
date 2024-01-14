package com.dk.apiversionone.springsecurity.jwtconfig;

import com.dk.apiversionone.springsecurity.authorities.JwtToPrincipalConverter;
import com.dk.apiversionone.springsecurity.authorities.UserPrincipalAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromRequest(request)
                .map(str-> jwtDecoder.decodedJWT(str))
                .map(str-> jwtToPrincipalConverter.convert(str))
                .map(UserPrincipalAuthenticationToken::new)
                .ifPresent(authentication->
                        SecurityContextHolder.getContext().setAuthentication(authentication)
                );

        filterChain.doFilter(request,response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")){
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
}
