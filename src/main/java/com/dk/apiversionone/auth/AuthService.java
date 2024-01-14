package com.dk.apiversionone.auth;

import com.dk.apiversionone.auth.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private static final String EXISTING_EMAIL = "dan@dan.com";

    public Optional<UserEntity> findByEmail(String email) {

        if (! EXISTING_EMAIL.equalsIgnoreCase(email)) return Optional.empty();

        var user = new UserEntity();
        user.setId(1L);
        user.setEmail(EXISTING_EMAIL);
        user.setPassword("$2a$12$eQbxmAGnSP2nqg6xVt0it.Pek1vEb7DFBQs.kJO/0GDaH1sR3DMmS");//dan https://bcrypt-generator.com/
        user.setRole("ROLE_ADMIN");
        user.setDescription("My nice admin");
        return Optional.of(user);
    }
}
