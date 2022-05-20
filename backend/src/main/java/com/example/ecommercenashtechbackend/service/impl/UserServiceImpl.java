package com.example.ecommercenashtechbackend.service.impl;

import com.example.ecommercenashtechbackend.dto.request.*;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserLoginResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.Cart;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.custom.ConflictException;
import com.example.ecommercenashtechbackend.exception.custom.ForbiddenException;
import com.example.ecommercenashtechbackend.exception.custom.UnauthorizedException;
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
    public UserResponseDto getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        userOptional.orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));
        return modelMapper.map(userOptional.get(), UserResponseDto.class);
    }

    @Override
    public UserResponseDto changePassword(UserChangePasswordRequestDto userChangePasswordRequestDto, Long userId) {
        Optional<User> oldUserOptional = userRepository.findById(userId);
        oldUserOptional.orElseThrow(() -> new NotFoundException("Not found user with id: " + userId));
        User oldUser = oldUserOptional.get();
        if (!passwordEncoder.matches(userChangePasswordRequestDto.getOldPassword(), oldUser.getPassword()))
            throw new UnauthorizedException("Password not match");
        oldUser.setPassword(passwordEncoder.encode(userChangePasswordRequestDto.getPassword()));
        User userSaved = userRepository.save(oldUser);
        return modelMapper.map(userSaved, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers(boolean deleted) {
        List<User> listUser = userRepository.findAllByDeleted(deleted);
        return util.mapList(listUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        Optional<User> userOldOptional = userRepository.findByEmail(userRequestDto.getEmail());
        if (userOldOptional.isPresent()) {
            throw new ConflictException("Email already exists");
        }

        Optional<Role> roleUserOptional = roleRepository.findByName(userRequestDto.getRole());
        roleUserOptional.orElseThrow(() -> new NotFoundException("Role " + userRequestDto.getRole() + " not found!"));

        User userSave = modelMapper.map(userRequestDto, User.class);
        userSave.setRoles(Set.of(roleUserOptional.get()));
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        if(userRequestDto.getRole().equals("USER")) {
            Cart cart = new Cart();
            userSave.setCart(cart);
        }

        User userSaved = userRepository.save(userSave);
        return modelMapper.map(userSaved, UserResponseDto.class);
    }

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {

        Optional<User> userOptional = userRepository.findByEmail(userLoginRequestDto.getEmail());
        userOptional.orElseThrow(() -> new ForbiddenException("Email or password is incorrect"));
        User user = userOptional.get();
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

    @Override
    public JwtResponse refreshToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        try {
            jwtUtil.validateToken(refreshTokenRequestDto.getRefreshToken());
        } catch (Exception e) {
            throw new ForbiddenException(e.getMessage());
        }
        String refreshToken = refreshTokenRequestDto.getRefreshToken();
        String email = jwtUtil.getUserNameFromJwtToken(refreshToken);
        Optional<User> userOptional = userRepository.findByEmail(email);
        userOptional.orElseThrow(() -> new NotFoundException("User not found"));
        UserDetail userDetail = new UserDetail(userOptional.get());
        String accessTokenNew = jwtUtil.generateAccessToken(userDetail);
        refreshToken = jwtUtil.generateRefreshToken(userDetail);
        return new JwtResponse(accessTokenNew, refreshToken);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
        Optional<User> userOld = userRepository.findById(userUpdateRequestDto.getId());
        userOld.orElseThrow(() -> new NotFoundException("User not found"));
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

    @Override
    public UserResponseDto updateBlockUser(UserStatusRequestDto userStatusRequestDto) {
        Optional<User> userOptional = userRepository.findById(userStatusRequestDto.getId());
        userOptional.orElseThrow(() -> new NotFoundException("User not found"));
        User userOld = userOptional.get();
        userOld.setBlocked(userStatusRequestDto.isBlocked());
        User userSaved = userRepository.save(userOld);
        return modelMapper.map(userSaved, UserResponseDto.class);
    }

    @Override
    public List<User> getListUser(int pageNumber, int pageSize, String sortField, String sortName, String keyword, boolean deleted) {
        Sort sort = Sort.by(sortField);
        sort = sortName.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        if (keyword != null) {
            return userRepository.findAll(keyword, deleted, pageable).getContent();
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
