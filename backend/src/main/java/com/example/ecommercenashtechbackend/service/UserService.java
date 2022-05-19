package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.*;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserLoginResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto getUserById(Long id);

    UserResponseDto changePassword(UserChangePasswordRequestDto userChangePasswordRequestDto, Long userId);

    List<UserResponseDto> getAllUsers(boolean deleted);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);

    JwtResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto updateBlockUser(UserStatusRequestDto userStatusRequestDto);

    List<User> getListUser(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted);

    void deleteUser(Long id);
}
