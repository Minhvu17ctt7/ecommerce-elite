package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.*;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserLoginResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.repository.RoleRepository;
import com.example.ecommercenashtechbackend.repository.UserRepository;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.security.jwt.JwtUtil;
import com.example.ecommercenashtechbackend.service.UserService;
import com.example.ecommercenashtechbackend.util.Util;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;
    private final Util util;

    @Override
    public List<UserResponseDto> getAllUsers(boolean deleted) {
        List<User> listUser = userRepository.findAllByDeleted(deleted);
        return util.mapList(listUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Role roleUser = roleRepository.findByName(userRequestDto.getRole());
        User userSave = modelMapper.map(userRequestDto, User.class);
        userSave.setRoles(Set.of(roleUser));
        Optional<User> userOld = userRepository.findByEmail(userSave.getEmail());
        if (userOld.isPresent()) {
            throw new ConflictException("Email already exists");
        }
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        User userSaved = userRepository.save(userSave);
        return modelMapper.map(userSaved, UserResponseDto.class);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        Optional<User> opt = userRepository.findByEmail(userLoginRequestDto.getEmail());

        if (opt.isPresent()) {
            User user = opt.get();

            if (!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
                throw new ForbiddenException("Email or password is incorrect");
            }

            if (user.isBlocked()) {
                throw new LockedException("User is locked");
            }

            UserDetail userDetail = new UserDetail(user);
            String accessToken = jwtUtil.generateAccessToken(userDetail);
            String refreshToken = jwtUtil.generateRefreshToken(userDetail);
            UserLoginResponseDto userLoginResponseDto = modelMapper.map(user, UserLoginResponseDto.class);
            userLoginResponseDto.setAccessToken(accessToken);
            userLoginResponseDto.setRefreshToken(refreshToken);
            return userLoginResponseDto;
        }
        throw new ForbiddenException("Email or password is incorrect");
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        try {
            jwtUtil.validateToken(refreshTokenRequestDto.getRefreshToken());
        } catch (Exception e) {
            throw new ForbiddenException(e.getMessage());
        }
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        String email = jwtUtil.getUserNameFromJwtToken(refreshToken);
        Optional<User> userOpt = userRepository.findByEmail(email);
        userOpt.orElseThrow(() -> new NotFoundException("User not found"));
        UserDetail userDetail = new UserDetail(userOpt.get());
        String accessTokenNew = jwtUtil.generateAccessToken(userDetail);
        refreshToken = jwtUtil.generateRefreshToken(userDetail);
        return new JwtResponse(accessTokenNew, refreshToken);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        Optional<User> userOld = userRepository.findById(userUpdateRequestDto.getId());
        if (userOld.isPresent()) {
            User userSave = userOld.get();
            BeanUtils.copyProperties(userUpdateRequestDto, userSave);
            if (userUpdateRequestDto.getRole() != null) {
                Role role = roleRepository.findByName(userUpdateRequestDto.getRole());
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                userSave.setRoles(roles);
            }
            User userSaved = userRepository.save(userSave);
            UserResponseDto userResponseDto = modelMapper.map(userSaved, UserResponseDto.class);
            return userResponseDto;
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public UserResponseDto updateBlockUser(UserStatusRequestDto userStatusRequestDto) {
        Optional<User> userOpt = userRepository.findById(userStatusRequestDto.getId());
        if (userOpt.isPresent()) {
            User userOld = userOpt.get();
            userOld.setBlocked(userStatusRequestDto.isBlocked());
            User userSaved = userRepository.save(userOld);
            return modelMapper.map(userSaved, UserResponseDto.class);
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public List<User> getListUser(int pageNumber, int pageSize, String sortField, String sortName, String keywork, boolean deleted) {
        Sort sort = Sort.by(sortField);
        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if (keywork != null) {
            return userRepository.findAll(keywork, deleted, pageable).getContent();
        }
        return userRepository.findAll(pageable).getContent();
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent() || userOpt.get().isDeleted()) {
            throw new NotFoundException("User not found");
        }
        userRepository.updateDeletedUserById(id);
    }

}
