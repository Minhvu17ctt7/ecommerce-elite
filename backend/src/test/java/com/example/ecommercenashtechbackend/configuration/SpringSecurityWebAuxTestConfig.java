package com.example.ecommercenashtechbackend.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        User admin = new User("admin", "admin", authorities);

        Collection<SimpleGrantedAuthority> authoritiesUser = new ArrayList<>();
        authoritiesUser.add(new SimpleGrantedAuthority("USER"));
        User user = new User("user", "user", authoritiesUser);

        return new InMemoryUserDetailsManager(Arrays.asList(admin, user));
    }
}
