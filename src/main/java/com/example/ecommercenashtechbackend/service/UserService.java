package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.*;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserLoginResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);

    JwtResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto);

    UserResponseDto updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto);

    UserResponseDto updateBlockUser(UserStatusRequestDto userStatusRequestDto);

    List<User> getListUser(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted);

    void deleteUser(Long id);
}
