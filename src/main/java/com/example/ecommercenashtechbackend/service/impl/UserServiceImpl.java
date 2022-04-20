package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.UserStatusRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserUpdateRequestDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private ModelMapper modelMapper = new ModelMapper();

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

            if (!passwordEncoder.matches(password, user.getPassword())) {
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
    public User updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        Optional<User> userOld = userRepository.findById(id);
        if (userOld.isPresent()) {
            User userSave = userOld.get();
            BeanUtils.copyProperties(userUpdateRequestDto, userSave);
            if(userUpdateRequestDto.getRole() != null){
                Role role = roleRepository.findByName(userUpdateRequestDto.getRole());
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                userSave.setRoles(roles);
            }
            User userSaved = userRepository.save(userSave);
            return userSaved;
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User enableUser(UserStatusRequestDto userStatusRequestDto) {
        Optional<User> userOpt = userRepository.findById(userStatusRequestDto.getId());
        if (userOpt.isPresent()) {
            User userOld = userOpt.get();
            userOld.setEnabled(userStatusRequestDto.isStatus());
            return userRepository.save(userOld);
        }
        throw new NotFoundException("User not found");
    }

    public void encodePassword(User user){
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    }
}
