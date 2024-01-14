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


    private static final String EXISTING_EMAIL = "admin@dan.com";
    private static final String ANOTHER_EMAIL = "dan@dan.com";

    public Optional<UserEntity> findByEmail(String email) {

        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(1L);
            user.setEmail(EXISTING_EMAIL);
            user.setPassword("$2a$12$eQbxmAGnSP2nqg6xVt0it.Pek1vEb7DFBQs.kJO/0GDaH1sR3DMmS");//dan https://bcrypt-generator.com/
            user.setRole("ROLE_ADMIN");
            user.setDescription("My nice admin");
            return Optional.of(user);
        }else  if (ANOTHER_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(99L);
            user.setEmail(ANOTHER_EMAIL);
            user.setPassword("$2a$12$eQbxmAGnSP2nqg6xVt0it.Pek1vEb7DFBQs.kJO/0GDaH1sR3DMmS");//dan https://bcrypt-generator.com/
            user.setRole("ROLE_USER");
            user.setDescription("My nice user");
            return Optional.of(user);

        }else {
            return Optional.empty();
        }
    }



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
