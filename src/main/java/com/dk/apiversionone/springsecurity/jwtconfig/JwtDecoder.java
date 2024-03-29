package com.dk.apiversionone.springsecurity.jwtconfig;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JwtDecoder {

    private final JwtProperties properties;

    public DecodedJWT decodedJWT(String token){

        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                .build().verify(token);
    }
}
