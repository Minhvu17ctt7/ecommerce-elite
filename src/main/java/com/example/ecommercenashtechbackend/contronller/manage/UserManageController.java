package com.example.ecommercenashtechbackend.contronller.manage;

import com.example.ecommercenashtechbackend.dto.request.UserRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserStatusRequestDto;
import com.example.ecommercenashtechbackend.dto.request.UserUpdateRequestDto;
import com.example.ecommercenashtechbackend.dto.response.JwtResponse;
import com.example.ecommercenashtechbackend.dto.response.UserResponseDto;
import com.example.ecommercenashtechbackend.entity.Role;
import com.example.ecommercenashtechbackend.entity.User;
import com.example.ecommercenashtechbackend.exception.ExceptionResponse;
import com.example.ecommercenashtechbackend.service.RoleService;
import com.example.ecommercenashtechbackend.service.UserService;
import com.example.ecommercenashtechbackend.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserManageController {

    private final UserService userService;
    private final RoleService roleService;
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<User>> getListUser() {
        List<User> users = getListUserPagination(1, "email", "asc", null).getBody();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{pageNumber}")
    public ResponseEntity<List<User>> getListUserPagination(@PathVariable("pageNumber") int pageNumber, @RequestParam("sortField") String sortField,
                                          @RequestParam("sortName") String sortName, @RequestParam("keyword") String keywork) {
        Page<User> page = userService.getListUser(pageNumber, sortField, sortName, keywork);
        List<User> users = page.getContent();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Create new user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "409", description = "Email is already in use",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PostMapping("/create-user")
    public ResponseEntity<UserRequestDto> saveUser(@Validated @RequestBody UserRequestDto userRequestCreateDto) {
        Role role = roleService.getRoleByName(userRequestCreateDto.getRole());
        User user = modelMapper.map(userRequestCreateDto, User.class);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        User userSave = userService.save(user);
        return ResponseEntity.ok(modelMapper.map(userSave, UserRequestDto.class));
    }

    @Operation(summary = "Update user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found user with this id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Validated @RequestBody UserUpdateRequestDto userRequestUpdateDto) {
        User userUpdated = userService.updateUser(id, userRequestUpdateDto);
        return ResponseEntity.ok(modelMapper.map(userUpdated, UserResponseDto.class));
    }

    @Operation(summary = "Update status user by admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success, user registered",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Missing require field see message for more details",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Not found user with this id",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @PutMapping("/update-user-status")
    public ResponseEntity<UserResponseDto> updateEnableUser(@Validated @RequestBody UserStatusRequestDto userStatusRequestDto) {
        User userUpdated = userService.enableUser(userStatusRequestDto);
        return ResponseEntity.ok(modelMapper.map(userUpdated, UserResponseDto.class));
    }
}


