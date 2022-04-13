package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
//        return userRepository.findAll();
        return null;
    }
}
