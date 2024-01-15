package com.dk.apiversionone.auth;

import com.dk.apiversionone.auth.model.LoginRequest;
import com.dk.apiversionone.auth.model.LoginResponse;
import com.dk.apiversionone.auth.model.UserEntity;
import com.dk.apiversionone.springsecurity.authorities.UserPrincipal;
import com.dk.apiversionone.springsecurity.jwtconfig.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private  final JwtIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;

    public LoginResponse fakeLogin(LoginRequest request){
        var token = jwtIssuer.issue(1L,request.getEmail(), List.of("USER"));
        return LoginResponse.builder().accessToken(token).build();
    }


    public LoginResponse login(LoginRequest request){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principals = (UserPrincipal) authentication.getPrincipal();

        var roles = principals.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        var token = jwtIssuer.issue(principals.getUserId(),principals.getEmail(), roles);
        return LoginResponse.builder().accessToken(token).build();
    }
}
