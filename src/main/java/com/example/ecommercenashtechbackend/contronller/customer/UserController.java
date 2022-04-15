package com.example.ecommercenashtechbackend.contronller.customer;

import com.example.ecommercenashtechbackend.common.RoleName;
import com.example.ecommercenashtechbackend.dto.request.UserLoginRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.security.jwt.JwtUtil;
import com.example.ecommercenashtechbackend.service.RoleService;
import com.example.ecommercenashtechbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Validated @RequestBody UserRequestDto userRequestDto) {
        ModelMapper modelMapper = new ModelMapper();
        Role roleUser = roleService.getRoleByName(RoleName.ROLE_USER);
        User userSave = modelMapper.map(userRequestDto, User.class);
        userSave.setRoles(Set.of(roleUser));
        userSave = userService.save(userSave);
        UserResponseDto responseDto = modelMapper.map(userSave, UserResponseDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginRequestDto userLoginDto) {
        User user = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
        UserDetail userDetail = new UserDetail(user);
        String accessToken = jwtUtil.generateAccessToken(userDetail);
        String refreshToken = jwtUtil.generateRefreshToken(userDetail);
        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
