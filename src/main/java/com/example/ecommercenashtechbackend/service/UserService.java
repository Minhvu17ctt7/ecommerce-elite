package com.example.ecommercenashtechbackend.service;

import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User save(User user);

    User login(String email, String password);

    User getUserById(Long id);

    User getUserByEmail(String email);
}
