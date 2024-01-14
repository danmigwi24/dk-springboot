package com.dk.apiversionone.auth;

import com.dk.apiversionone.auth.model.LoginRequest;
import com.dk.apiversionone.auth.model.LoginResponse;
import com.dk.apiversionone.springsecurity.authorities.UserPrincipal;
import com.dk.apiversionone.springsecurity.jwtconfig.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthSecurityController {

    private  final JwtIssuer jwtIssuer;

    private final AuthenticationManager authenticationManager;


    @GetMapping("/")
    public String home(){
        return ("Welcome");
    }

    @PostMapping("/fakelogin")
    public LoginResponse fakeLogin(@RequestBody @Validated LoginRequest request){
        var token = jwtIssuer.issue(1L,request.getEmail(), List.of("USER"));
        return LoginResponse.builder().accessToken(token).build();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principals = (UserPrincipal) authentication.getPrincipal();

        var roles = principals.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        var token = jwtIssuer.issue(principals.getUserId(),principals.getEmail(), roles);
        return LoginResponse.builder().accessToken(token).build();
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal principal){
        var email = principal.getEmail();
        var userId = principal.getUserId();
        return ("Welcome USER \n User ID :"+ userId+ " \nEmail : "+email);
    }


    @GetMapping("/admin")
    public String admin(){
        return ("Welcome ADMIN");
    }


}
