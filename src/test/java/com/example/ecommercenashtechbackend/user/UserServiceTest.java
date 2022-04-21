package com.example.ecommercenashtechbackend.user;

import com.example.ecommercenashtechbackend.dto.request.UserStatusRequestDto;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.UserService;
import com.example.ecommercenashtechbackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.webjars.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private User userInitial;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(passwordEncoder, userRepository, roleRepository);
        userInitial = User.builder().email("Minhvu@gmail.com").password("12345").firstName("Hoang").lastName("Vu").build();
    }

    @Test
    public void login_ShouldThrownForbiddenException_WhenEmailNotExist() {
        when(userRepository.findByEmail(any())).thenThrow(new ForbiddenException("Username or password is incorrect"));
        assertThrows(ForbiddenException.class, () -> userService.login("Minhvu@gmail.com", "123456"));
    }

    @Test
    public void login_ShouldThrownForbiddenException_WhenPasswordNotMatch() {
        when(userRepository.findByEmail("Minhvu@gmail.com")).thenReturn(java.util.Optional.ofNullable(userInitial));
        when(passwordEncoder.matches("1234", "12345")).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> userService.login("Minhvu@gmail.com","1234"));
    }

    @Test
    public void login_ShouldThrowLockedException_WhenUserDisable() {
        userInitial.setBlocked(false);
        when(passwordEncoder.matches("12345", "12345")).thenReturn(true);
        when(userRepository.findByEmail("Minhvu@gmail.com")).thenReturn(java.util.Optional.of(userInitial));
        assertThrows(LockedException.class, () -> userService.login("Minhvu@gmail.com", "12345"));
    }

    @Test
    public void login_ShouldReturnUser_WhenLoginSuccess() {
        userInitial.setBlocked(true);
        when(userRepository.findByEmail("Minhvu@gmail.com")).thenReturn(java.util.Optional.ofNullable(userInitial));
        when(passwordEncoder.matches("12345", "12345")).thenReturn(true);
        assertThat(userService.login("Minhvu@gmail.com", "12345")).isEqualTo(userInitial);
    }

    @Test
    public void save_ShouldReturnUser_WhenRegisterSuccess() {
        User userInitial = User.builder().email("Minhvu@gmail.com").password("12345").firstName("Hoang").lastName("Vu").build();
        when(userRepository.findByEmail("Minhvu@gmail.com")).thenReturn(java.util.Optional.ofNullable(null));
        when(userRepository.save(any())).thenReturn(userInitial);
        User result = userService.save(userInitial);
        assertThat(result.getEmail()).isEqualTo(userInitial.getEmail());
        assertThat(result.getFirstName()).isEqualTo(userInitial.getFirstName());
        assertThat(result.getLastName()).isEqualTo(userInitial.getLastName());
    }

    @Test
    public void login_ShouldThrowConflictException_WhenRegisterEmailExist() {
        User userInitial = User.builder().email("Minhvu@gmail.com").password("12345").firstName("Hoang").lastName("Vu").build();
        when(userRepository.findByEmail("Minhvu@gmail.com")).thenReturn(java.util.Optional.ofNullable(userInitial));
        assertThrows(ConflictException.class, () -> userService.save(userInitial));
    }

    @Test
    public void enableUser_shouldThrownNotFoundException_whenUserNotFound() {
        UserStatusRequestDto userStatusRequestDto = UserStatusRequestDto.builder().id(Long.valueOf(1)).status(false).build();
        when(userRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.ofNullable(null));
        assertThrows(NotFoundException.class, () -> userService.enableUser(userStatusRequestDto));
    }

//    @Test
//    public void enableUser_shouldUserEnableIsTrue_whenUserFounded() {
//        UserStatusRequestDto userStatusRequestDto = UserStatusRequestDto.builder().id(Long.valueOf(1)).status(false).build();
//        when(userRepository.findById(Long.valueOf(1))).thenReturn(java.util.Optional.ofNullable(userInitial));
//        userInitial.setEnabled(true);
//        when(userRepository.save(any())).thenReturn(userInitial);
//        User result = userService.enableUser(userStatusRequestDto);
//        assertThat(result.isEnabled()).isEqualTo(true);
//    }
}
