package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.UserLoginRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.dto.response.UserLoginResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.security.jwt.JwtUtil;
import com.example.ecommercenashtechbackend.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private JwtUtil jwtUtil;
    private Util util;
    private ModelMapper modelMapper;
    private User userInitial;
    private UserLoginRequestDto userLoginRequestDto;
    private UserRequestDto userRequestDto;
    private UserResponseDto userResponseDto;
    private Role role;


    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        modelMapper = mock(ModelMapper.class);
        jwtUtil = mock(JwtUtil.class);
        util = mock(Util.class);
        userInitial = mock(User.class);
        userLoginRequestDto = mock(UserLoginRequestDto.class);
        userRequestDto = mock(UserRequestDto.class);
        userResponseDto = mock(UserResponseDto.class);
        role = mock(Role.class);
        userService = new UserServiceImpl(passwordEncoder, userRepository, roleRepository, modelMapper, jwtUtil, util);
        when(jwtUtil.generateAccessToken(any())).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        when(jwtUtil.generateRefreshToken(any())).thenReturn("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ik1pbmh2dSIsImlkIjoiMSIsImlhdCI6MTUxNjIzOTAyMn0.gzNQnKOmWScnrMgcCdACiPT3NBjAfJ8sQeDBagcg8PA");
    }

    @Test
    public void login_ShouldThrownForbiddenException_WhenUserNotFindByEmail() {
        when(userRepository.findByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.ofNullable(null));
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () -> userService.login(userLoginRequestDto));
        assertThat(forbiddenException.getMessage()).isEqualTo("Email or password is incorrect");
    }

    @Test
    public void login_ShouldThrownForbiddenException_WhenPasswordNotMatch() {
        when(userRepository.findByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.ofNullable(userInitial));
        when(passwordEncoder.matches(userLoginRequestDto.getPassword(), userInitial.getPassword())).thenReturn(false);
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () -> userService.login(userLoginRequestDto));
        assertThat(forbiddenException.getMessage()).isEqualTo("Email or password is incorrect");
    }

    @Test
    public void login_ShouldThrowLockedException_WhenUserBlocked() {
        User user = User.builder().blocked(true).build();
        when(userRepository.findByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())).thenReturn(true);
        LockedException lockedException = assertThrows(LockedException.class, () -> userService.login(userLoginRequestDto));
        assertThat(lockedException.getMessage()).isEqualTo("User is locked");
    }

    @Test
    public void login_ShouldReturnUserLoginResponseDto_WhenLoginSuccess() {
        UserLoginResponseDto userLoginResponseDto = mock(UserLoginResponseDto.class);
        when(userRepository.findByEmail(userLoginRequestDto.getEmail())).thenReturn(Optional.ofNullable(userInitial));
        when(passwordEncoder.matches(userLoginRequestDto.getPassword(), userInitial.getPassword())).thenReturn(true);
        when(modelMapper.map(userInitial, UserLoginResponseDto.class)).thenReturn(userLoginResponseDto);
        UserLoginResponseDto result = userService.login(userLoginRequestDto);
        assertThat(result.getAccessToken()).isEqualTo("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        assertThat(result.getRefreshToken()).isEqualTo("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6Ik1pbmh2dSIsImlkIjoiMSIsImlhdCI6MTUxNjIzOTAyMn0.gzNQnKOmWScnrMgcCdACiPT3NBjAfJ8sQeDBagcg8PA");
    }

    @Test
    public void createUser_ShouldThrowConflict_WhenEmailExist() {
        when(roleRepository.findByName(userRequestDto.getRole())).thenReturn(role);
        when(modelMapper.map(userRequestDto, User.class)).thenReturn(userInitial);
        when(userRepository.findByEmail(userInitial.getEmail())).thenReturn(Optional.of(userInitial));
        ConflictException conflictException = assertThrows(ConflictException.class, () -> userService.createUser(userRequestDto));
        assertThat(conflictException.getMessage()).isEqualTo("Email already exists");
    }

    @Test
    public void createUser_ShouldReturnUserResponseDto_WhenCreateUserSuccess() {
        when(roleRepository.findByName(userRequestDto.getRole())).thenReturn(role);
        when(modelMapper.map(userRequestDto, User.class)).thenReturn(userInitial);
        when(userRepository.findByEmail(userInitial.getEmail())).thenReturn(Optional.ofNullable(null));
        when(passwordEncoder.encode(userInitial.getPassword())).thenReturn("67890");
        when(userRepository.save(any())).thenReturn(userInitial);
        when(modelMapper.map(userInitial, UserResponseDto.class)).thenReturn(userResponseDto);
        UserResponseDto result = userService.createUser(userRequestDto);
        assertThat(result).isEqualTo(userResponseDto);
    }

}
