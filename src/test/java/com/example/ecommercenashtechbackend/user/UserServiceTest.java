package com.example.ecommercenashtechbackend.user;

import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.UserService;
import com.example.ecommercenashtechbackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        when(userRepository.findByEmail(any())).thenThrow(new ForbiddenException("Username or password is incorrect"));
    }

    @Test
    public void when_EmailNotExist_Expect_ThrowConflictException() {
        assertThrows(ForbiddenException.class, () -> userService.login("Minhvu@gmail.com", "123456"));
    }

}
