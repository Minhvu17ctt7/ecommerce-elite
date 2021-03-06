package com.example.ecommercenashtechbackend.controller.customer;

import com.example.ecommercenashtechbackend.dto.request.UserChangePasswordRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.ResponseDto;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.security.UserDetail;
import com.example.ecommercenashtechbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUserDetail(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;
        userUpdateRequestDto.setId(userDetail.getUser().getId());
        userUpdateRequestDto.setRole(null);

        UserResponseDto userResponseDto = userService.updateUser(userUpdateRequestDto);
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(200, userResponseDto, "Update user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserDetail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        UserResponseDto userResponseDto = userService.getUserById(userDetail.getUser().getId());
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(200, userResponseDto, "Get user successfully");
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ResponseDto<UserResponseDto>> changePassword(@RequestBody UserChangePasswordRequestDto userChangePasswordRequestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail)principal;

        UserResponseDto userResponseDto = userService.changePassword(userChangePasswordRequestDto, userDetail.getUser().getId());
        ResponseDto<UserResponseDto> responseDto = new ResponseDto<>(200, userResponseDto, "Change password successfully");
        return ResponseEntity.ok(responseDto);
    }

}
