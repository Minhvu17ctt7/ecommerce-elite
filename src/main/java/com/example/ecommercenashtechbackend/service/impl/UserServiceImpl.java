package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> getAllUsers() {
        List<User> listUser = userRepository.findAll();
        return listUser;
    }


    @Override
    public User save(User user) {
        Optional<User> userOld = userRepository.findByEmail(user.getEmail());
        if (userOld.isPresent()) {
            throw new ConflictException("Email already exists");
        }
        encodePassword(user);
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {

        Optional<User> opt = userRepository.findByEmail(email);

        if (opt.isPresent()) {
            User user = opt.get();

            if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
                throw new ForbiddenException("Username or password is incorrect");
            }

            if (!user.isEnabled()) {
                throw new LockedException("User is locked");
            }

            return user;
        }
        throw new ForbiddenException("Username or password is incorrect");
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void encodePassword(User user){
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
