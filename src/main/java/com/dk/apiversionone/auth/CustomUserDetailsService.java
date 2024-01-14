package com.dk.apiversionone.auth;

import com.dk.apiversionone.auth.model.UserEntity;
import com.dk.apiversionone.simulation.DataSourceService;
import com.dk.apiversionone.springsecurity.authorities.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    //private  final DataSourceService dataSourceService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = findByEmail(username).orElseThrow();

        return UserPrincipal.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
                .build();
    }

    /**
     * THIS SHOULD BE CHANGED TO USE DB DATA
     */
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
