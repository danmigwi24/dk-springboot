package com.dk.apiversionone.simulation;

import com.dk.apiversionone.auth.model.UserEntity;
import com.dk.apiversionone.springsecurity.jwtconfig.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataSourceService {

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
        } else if (ANOTHER_EMAIL.equalsIgnoreCase(email)) {
            var user = new UserEntity();
            user.setId(99L);
            user.setEmail(ANOTHER_EMAIL);
            user.setPassword("$2a$12$eQbxmAGnSP2nqg6xVt0it.Pek1vEb7DFBQs.kJO/0GDaH1sR3DMmS");//dan https://bcrypt-generator.com/
            user.setRole("ROLE_USER");
            user.setDescription("My nice user");
            return Optional.of(user);

        } else {
            return Optional.empty();
        }
    }


}
