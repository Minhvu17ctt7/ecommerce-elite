package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.UserStatusRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserUpdateRequestDto;
import com.example.ecommercenashtechbackend.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User save(User user);

    User login(String email, String password);

    User updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto);

    User getUserById(Long id);

    User getUserByEmail(String email);

    User enableUser(UserStatusRequestDto userStatusRequestDto);

    Page<User> getListUser(int pageNumber, String sortField, String sortName, String keywork);
}
