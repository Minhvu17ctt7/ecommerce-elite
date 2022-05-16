package com.example.ecommercenashtechbackend.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        UserActive basicActiveUser = new UserActive("user@gmail.com", "admin", Arrays.asList(
                new SimpleGrantedAuthority("USER")
        ));

        UserActive basicActiveAdmin = new UserActive("admin@gmail.com", "user", Arrays.asList(
                new SimpleGrantedAuthority("ADMIN")
        ));
        return new InMemoryUserDetailsManager(Arrays.asList(
                basicActiveUser, basicActiveAdmin
        ));
    }
}
