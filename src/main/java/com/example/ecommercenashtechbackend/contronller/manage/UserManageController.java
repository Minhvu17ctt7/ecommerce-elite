package com.example.ecommercenashtechbackend.contronller.manage;

import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.service.RoleService;
import com.example.ecommercenashtechbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserManageController {

    private final UserService userService;
    private final RoleService roleService;
    private ModelMapper modelMapper = new ModelMapper();


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = new ArrayList<>();
        users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create new user by admin")
    @PostMapping("/create-user")
    public ResponseEntity<UserRequestDto> saveUser(@Validated @RequestBody UserRequestDto userRequestCreateDto) {
        Role role = roleService.getRoleByName(userRequestCreateDto.getRole());
        User user = modelMapper.map(userRequestCreateDto, User.class);
        user.setRoles(Set.of(role));
        User userSave = userService.save(user);
        return ResponseEntity.ok(modelMapper.map(userSave, UserRequestDto.class));
    }

}


