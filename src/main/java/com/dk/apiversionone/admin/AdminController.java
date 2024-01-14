package com.dk.apiversionone.admin;

import com.dk.apiversionone.auth.AuthService;
import com.dk.apiversionone.auth.model.LoginRequest;
import com.dk.apiversionone.auth.model.LoginResponse;
import com.dk.apiversionone.springsecurity.authorities.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/admin")
public class AdminController {

    private final AuthService authService;

    @GetMapping("/details")
    public String user(@AuthenticationPrincipal UserPrincipal principal){
        var email = principal.getEmail();
        var userId = principal.getUserId();
        return ("Welcome Admin \n User ID :"+ userId+ " \nEmail : "+email);
    }


}
